package com.cognifide.slung.condition.base

import spock.lang.*
import com.cognifide.slung.condition.ConditionContext
import javax.servlet.ServletRequest
import org.apache.sling.api.SlingHttpServletRequest
import org.apache.sling.api.resource.Resource
import org.apache.sling.api.request.RequestPathInfo

class SameRequestPathConditionSpec extends Specification {

  SameRequestPathCondition testedObject = new SameRequestPathCondition()

  ConditionContext context = Mock()

  ServletRequest request = Mock()

  SlingHttpServletRequest slingRequest = Mock()

  Resource resource = Mock()

  RequestPathInfo requestPathInfo = Mock()

  def 'should fail when condition context has no request'() {
    when: 'condition is checked'
      final def actual = testedObject.test(context)

    then: 'condition is not met'
      !actual
  }

  def 'should fail when request is not SlingHttpServletRequest'() {
    given: 'request'
      context.request >> request

    when: 'condition is checked'
      final def actual = testedObject.test(context)

    then: 'condition is not met'
      !actual
  }

  @Unroll
  def 'should fail when resource path does not start with request path'() {
    given: 'resource'
      context.resource >> resource
      resource.path >> resourcePath
    and: 'request'
      context.request >> slingRequest
      slingRequest.requestPathInfo >> requestPathInfo
      requestPathInfo.resourcePath >> requestPath

    when: 'condition is checked'
      final def actual = testedObject.test(context)

    then: 'condition is not met'
      !actual

    where:
      resourcePath                       | requestPath
      null                               | null
      '/content/home/homepage/component' | null
      null                               | '/content/home/page'
      '/content/home/homepage/component' | '/content/home/page'
      '/content/home/homepage/component' | '/content/home/page/sub-page'
  }

  def 'should succeed when resource path starts with request path'() {
    given: 'resource'
      context.resource >> resource
      resource.path >> '/content/home/homepage/component'
    and: 'request'
      context.request >> slingRequest
      slingRequest.requestPathInfo >> requestPathInfo
      requestPathInfo.resourcePath >> '/content/home/homepage'

    when: 'condition is checked'
      final def actual = testedObject.test(context)

    then: 'condition is met'
      actual
  }
} 