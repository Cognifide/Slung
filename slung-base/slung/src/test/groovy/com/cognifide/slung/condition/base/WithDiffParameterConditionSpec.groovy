package com.cognifide.slung.condition.base

import spock.lang.*
import com.cognifide.slung.condition.ConditionContext
import javax.servlet.ServletRequest

class WithDiffParameterConditionSpec extends Specification {

  static final DIFF_TO_PARAMETER = 'cq_diffTo'

  static final DIFF_TO_PARAMETER_VALUE = '1.0'

  WithDiffParameterCondition testedObject = new WithDiffParameterCondition()

  ConditionContext context = Mock()

  ServletRequest request = Mock()

  def 'should fail when condition context has no request'() {
    when: 'condition is checked'
      final def actual = testedObject.test(context)

    then: 'condition is not met'
      !actual
  }

  def 'should fail when request has no "cq_diffTo" parameter'() {
    given: 'request'
      context.request >> request

    when: 'condition is checked'
      final def actual = testedObject.test(context)

    then: 'condition is not met'
      !actual
  }

  def 'should succeed when request has "cq_diffTo" parameter'() {
    given: 'request with "cq_diffTo" parameter'
      context.request >> request
      request.getParameter(DIFF_TO_PARAMETER) >> DIFF_TO_PARAMETER_VALUE

    when: 'condition is checked'
      final def actual = testedObject.test(context)

    then: 'condition is met'
      actual
  }
} 