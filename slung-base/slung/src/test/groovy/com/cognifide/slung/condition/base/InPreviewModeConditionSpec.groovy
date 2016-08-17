package com.cognifide.slung.condition.base

import spock.lang.*
import com.cognifide.slung.condition.ConditionContext
import com.day.cq.wcm.api.WCMMode
import javax.servlet.ServletRequest

class InPreviewModeConditionSpec extends Specification {

  static final WCM_MODE_PARAMETER =  WCMMode.name

  ConditionContext context = Mock()

  ServletRequest request = Mock()

  InPreviewModeCondition testedObject = new InPreviewModeCondition()

  def 'should fail when condition context has no request'() {
    given: 'no request'

    when: 'condition is checked'
      final def actual = testedObject.test(context)

    then: 'condition is not met'
      !actual
  }

  @Unroll
  def 'condition should fail when request "com.day.cq.wcm.api.WCMMode" argument "#mode" is not WCMMode.PREVIEW'() {
    given: 'context'
      context.request >> request
      request.getAttribute(WCM_MODE_PARAMETER) >> mode

    when: 'condition is checked'
      final def actual = testedObject.test(context)

    then: 'condition is not met'
      !actual

    where:
      mode << [null, WCMMode.DISABLED, WCMMode.EDIT, WCMMode.ANALYTICS, WCMMode.READ_ONLY, WCMMode.DESIGN]
  }

  def 'condition should succeed when request has "com.day.cq.wcm.api.WCMMode" argument set to WCMMode.PREVIEW'() {
    given: 'context'
      context.request >> request
      request.getAttribute(WCM_MODE_PARAMETER) >> WCMMode.PREVIEW

    when: 'condition is checked'
      final def actual = testedObject.test(context)

    then: 'condition is met'
      actual
  }
}