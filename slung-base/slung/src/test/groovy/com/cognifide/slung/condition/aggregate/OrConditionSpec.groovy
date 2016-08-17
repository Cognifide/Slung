package com.cognifide.slung.condition.aggregate

import spock.lang.*
import com.cognifide.slung.condition.Condition
import com.cognifide.slung.condition.ConditionContext

class OrConditionSpec extends Specification {

  ConditionContext context = Mock()

  Condition condition1 = Mock()

  Condition condition2 = Mock()

  Condition condition3 = Mock()

  OrCondition testedObject

  def 'should throw null pointer exception when creating object from null parameters'() {
    when: 'condition is created'
      testedObject = new OrCondition(null)
      
    then: 'exception is thrown'
      thrown(NullPointerException)
  }

  def 'condition should fail when there are no conditons'() {
    given: 'no conditions'
      def conditions = []
    and: '"or" condition receive conditions'
      testedObject = new OrCondition(conditions)

    when: 'condition is checked'
      final def actual = testedObject.test(context)

    then: 'condition is not met'
      !actual
  }

  def 'condition should fail when all conditions are false'() {
    given: 'conditions'
      def conditions = [condition1, condition2, condition3]
    and: '"or" condition receive conditions'
      testedObject = new OrCondition(conditions)

    when: '"or" condition is checked'
      final def actual = testedObject.test(context)

    then: 'all conditions are checked if at least one is true'
      1 * condition1.test(context) >> false
      1 * condition2.test(context) >> false
      1 * condition3.test(context) >> false
    and: 'condition is not met'
      !actual
  }

  @Unroll
  def 'condition should succeed when at least one condition is true'() {
    given: 'conditions'
      def conditions = [condition1, condition2, condition3]
    and: '"or" condition receive conditions'
      testedObject = new OrCondition(conditions)

    when: '"or" condition is checked'
      final def actual = testedObject.test(context)

    then: 'first condition is always checked for truth'
      1 * condition1.test(context) >> result1
    and: 'rest of conditions is checked for truth only if required'
      numberOfInvocations2 * condition2.test(context) >> result2
      numberOfInvocations3 * condition3.test(context) >> result3
    and: 'condition is met'
      actual

    where:
      result1 | result2 | numberOfInvocations2 | result3 | numberOfInvocations3
      true    | false   | 0                    | false   | 0
      true    | true    | 0                    | false   | 0
      true    | false   | 0                    | true    | 0
      true    | true    | 0                    | true    | 0
      false   | true    | 1                    | false   | 0
      false   | true    | 1                    | true    | 0
      false   | false   | 1                    | true    | 1
  }
} 