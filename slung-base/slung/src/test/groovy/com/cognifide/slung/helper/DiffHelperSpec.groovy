package com.cognifide.slung.helper

import com.day.cq.wcm.api.WCMMode
import javax.servlet.ServletRequest
import org.apache.sling.api.resource.Resource
import org.apache.sling.api.resource.ValueMap
import spock.lang.Specification
import spock.lang.Unroll

class DiffHelperSpec extends Specification {

  static final WCM_MODE_PARAMETER = WCMMode.name

  static final DIFF_TO_PARAMETER = 'cq_diffTo'

  static final PRIMARY_TYPE_PROPERTY = 'jcr:primaryType'

  static final FROZEN_NODE = 'nt:frozenNode'

  ServletRequest request = Mock()

  Resource resource = Mock()

  ValueMap valueMap = Mock()

  def 'should respond false when request is null when asked about preview mode'() {
    given: 'no request'

    when: 'asked if preview mode is on'
      final def actual = DiffHelper.isPreviewMode(null)

    then: 'should respond false'
      !actual
  }

  @Unroll
  def 'should respond false when request has not "com.day.cq.wcm.api.WCMMode" argument set to WCMMode.PREVIEW'() {
    given: 'request'
      request.getAttribute(WCM_MODE_PARAMETER) >> mode

    when: 'asked if preview mode is on'
      final def actual = DiffHelper.isPreviewMode(request)

    then: 'should respond false'
      !actual

    where:
      mode << [null, WCMMode.DISABLED, WCMMode.EDIT, WCMMode.ANALYTICS, WCMMode.READ_ONLY, WCMMode.DESIGN]
  }

  def 'should respond true when request has "com.day.cq.wcm.api.WCMMode" argument set to WCMMode.PREVIEW'() {
    given: 'request'
      request.getAttribute(WCM_MODE_PARAMETER) >> WCMMode.PREVIEW

    when: 'asked if in preview mode'
      final def actual = DiffHelper.isPreviewMode(request)

    then: 'should respond true'
      actual
  }

  def 'should respond false when request is null'() {
    when: 'asked if has diff parameter'
      final def actual = DiffHelper.hasDiffParameter(null)

    then: 'should respond false'
      !actual
  }

  def 'should respond false when request has not diff parameter'() {
    given: 'request without diff parameter'
      request.getParameter(DIFF_TO_PARAMETER) >> null

    when: 'asked about diff parameter'
      final def actual = DiffHelper.hasDiffParameter(request)

    then: 'should respond false'
      !actual
  }

  def 'should respond true when request has diff parameter'() {
    given: 'request with diff parameter'
      request.getParameter(DIFF_TO_PARAMETER) >> '1.0'

    when: 'asked about diff parameter'
      final def actual = DiffHelper.hasDiffParameter(request)

    then: 'should respond true'
      actual
  }

  def 'should respond false when resource is null'() {
    when: 'asked about if resource is frozen'
      final def actual = DiffHelper.isFrozenNode(null)

    then: 'should respond false'
      !actual
  }

  def 'should respond true when resource primary type is "nt:frozenNode"'() {
    given: 'resource with value map'
      resource.valueMap >> valueMap
      valueMap.get(PRIMARY_TYPE_PROPERTY, String) >> type

    when: 'resource primary type is checked'
      final def actual = DiffHelper.isFrozenNode(resource)

    then: 'resource is not a frozen node'
      !actual

    where:
      type << [null, 'nt:file']
  }

  def 'should respond true when resource primary type is "nt:frozenNode"'() {
    given: 'resource with value map'
      resource.valueMap >> valueMap
      valueMap.get(PRIMARY_TYPE_PROPERTY, String) >> FROZEN_NODE

    when: 'resource primary type is checked'
      final def actual = DiffHelper.isFrozenNode(resource)

    then: 'resource is frozen node'
      actual
  }
}