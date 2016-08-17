package com.cognifide.slung.condition.base

import spock.lang.*
import com.cognifide.slung.condition.ConditionContext

class NoValueConditionSpec extends Specification {

  ConditionContext context = Mock()

  NoValueCondition testedObject = new NoValueCondition()

  @Unroll
  def 'should succeed when value and default value are not set'() {
    given: 'context with value and default value not set'
      context.value >> value
      context.defaultValue >> defaultValue

    when: 'condition is checked'
     final def actual = testedObject.test(context)

    then: 'condition is met'
      actual

    where:
      value | defaultValue
      null  | null
      null  | [] as String[]
  }

  @Unroll
  def 'should fail when value or default value is set'() {
    given: 'context with value or/and default value set'
      context.value >> value
      context.defaultValue >> defaultValue

    when: 'condition is checked'
     final def actual = testedObject.test(context)

    then: 'condition is not met'
      !actual

    where:
      value | defaultValue
      ''    | null
      null  | ''
      null  | [''] as String[]
      ''    | ''
      ''    | [''] as String[]
  }
}