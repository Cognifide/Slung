package com.cognifide.slung.condition.base


import com.cognifide.slung.condition.ConditionContext
import org.apache.sling.api.resource.Resource
import org.apache.sling.api.resource.ValueMap
import spock.lang.Specification
import spock.lang.Unroll

class FrozenConditionSpec extends Specification {

  static final PRIMARY_TYPE_PROPERTY = 'jcr:primaryType'

  static final FROZEN_NODE = 'nt:frozenNode'

  ConditionContext context = Mock()

  Resource resource = Mock()

  ValueMap valueMap = Mock()

  FrozenCondition testedObject = new FrozenCondition()

  @Unroll
  def 'should fail when resource primary type #type is not "nt:frozenNode"'() {
    given: 'resource'
      context.resource >> resource
      resource.valueMap >> valueMap
      valueMap.get(PRIMARY_TYPE_PROPERTY, String) >> type

    when: 'condition is checked'
      final def actual = testedObject.test(context)

    then: 'condition is not'
      !actual

    where:
      type << [null, 'nt:file']
  }

  def 'should succeed when primary type of resource is "nt:frozenNode"'() {
    given: 'resource'
      context.resource >> resource
      resource.valueMap >> valueMap
      valueMap.get(PRIMARY_TYPE_PROPERTY, String) >> FROZEN_NODE

    when: 'condition is checked'
      final def actual = testedObject.test(context)

    then: 'condition is not'
      actual
  }
}