package com.cognifide.slung.helper

import org.apache.sling.api.SlingHttpServletRequest
import org.apache.sling.api.request.RequestPathInfo
import org.apache.sling.api.resource.Resource
import spock.lang.Specification
import spock.lang.Unroll

class PathHelperSpec extends Specification {

  SlingHttpServletRequest request = Mock()

  RequestPathInfo requestPathInfo = Mock()

  Resource resource = Mock()

  def 'should throw null pointer exception when request is null'() {
    when: 'null is passed'
      PathHelper.resourcePathStartsWithRequestPath(null)

    then: 'null pointer exception will be thrown'
      thrown NullPointerException
  }

  @Unroll
  def 'should respond false when resource path #resourcePath does not start with request path #requestPath'() {
    given: 'request'
      request.resource >> resource
      request.requestPathInfo >> requestPathInfo
    and: 'information about paths'
      resource.path >> resourcePath
      requestPathInfo.resourcePath >> requestPath

    when: 'asked if resource path starts with request path'
      final def actual = PathHelper.resourcePathStartsWithRequestPath(request)

    then: 'is false'
      !actual

    where:
      resourcePath     | requestPath
      null             | null
      '/resource/path' | '/request/path'
  }

  def 'should respond true if resource path starts with request path'() {
    given: 'request'
      request.resource >> resource
      request.requestPathInfo >> requestPathInfo
    and: 'information about paths'
      resource.path >> '/path/resource'
      requestPathInfo.resourcePath >> '/path'

    when: 'asked if resource path starts with request path'
      final def actual = PathHelper.resourcePathStartsWithRequestPath(request)

    then: 'is true'
      actual
  }
}