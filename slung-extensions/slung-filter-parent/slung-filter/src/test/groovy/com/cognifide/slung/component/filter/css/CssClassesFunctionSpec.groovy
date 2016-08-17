package com.cognifide.slung.component.filter.css

import com.cognifide.slung.api.configuration.CssClassesConfiguration
import com.google.common.base.Joiner
import java.util.Date;
import org.apache.jackrabbit.JcrConstants
import org.apache.sling.api.resource.Resource
import org.apache.sling.api.resource.ValueMap
import spock.lang.Specification
import spock.lang.Unroll

class CssClassesFunctionSpec extends Specification {

  static final MODIFIED_CLASSES = ['class', 'class']

  static final Joiner joiner = Joiner.on(' ').skipNulls()

  CssClassesConfiguration cssClassesConfiguration = Mock()

  Resource resource = Mock()

  ValueMap valueMap = Mock()

  Resource oldResource = Mock()

  ValueMap oldValueMap = Mock()

  CssClassesFunction testedObject = new CssClassesFunction(cssClassesConfiguration, resource, joiner)

  @Unroll
  def 'should return empty string when #lastModified equals #oldLastModified, concatenated modified classes otherwise'() {
    when: 'function is applied'
      final def actual = testedObject.apply(oldResource)

    then: 'last modifed is taken from resource'
      1 * resource.getValueMap() >> valueMap
      1 * valueMap.get(JcrConstants.JCR_LASTMODIFIED, Date.class) >> lastModified
    and: 'last modified is taken from old resource'
      1 * oldResource.getValueMap() >> oldValueMap
      1 * oldValueMap.get(JcrConstants.JCR_LASTMODIFIED, Date.class) >> oldLastModified
    and: 'css classes configuration might be asked for classes'
      numberOfInvocations * cssClassesConfiguration.getModifiedCssClasses() >> MODIFIED_CLASSES
      0 * _

    and: 'css classes are constructed as expected, emtpy string when no difference between last modified dates, concatenated modified css classes otherwise'
      expected == actual

    where:
      lastModified         | oldLastModified      | numberOfInvocations || expected
      null                 | null                 | 0                   || ''
      toDate('2016/01/01') | null                 | 1                   || 'class class'
      null                 | toDate('2016/01/01') | 1                   || 'class class'
      toDate('2016/01/01') | toDate('2016/01/01') | 0                   || ''
      toDate('2016/01/01') | toDate('2016/01/02') | 1                   || 'class class'
  }
  
  Date toDate(String s) {
    new Date().parse('yyyy/MM/dd', s)
  }
}

