package com.cognifide.slung.condition.aggregate

import spock.lang.*
import com.cognifide.slung.condition.Condition
import com.cognifide.slung.condition.ConditionContext

class AndConditionSpec extends Specification {

  ConditionContext context = Mock()

  Condition condition1 = Mock()

  Condition condition2 = Mock()

  Condition condition3 = Mock()

  AndCondition testedObject

  def 'should throw null pointer exception when creating object from null parameters'() {
    when: 'condition is created'
      testedObject = new AndCondition(null)
      
    then: 'exception is thrown'
      thrown(NullPointerException)
  }

  def 'should succeed when there are no conditons'() {
    given: 'no conditions'
      def conditions = []
    and: '"and" condition receive conditions'
      testedObject = new AndCondition(conditions)

    when: 'condition is checked'
      final def actual = testedObject.test(context)

    then: 'condition is met'
      actual
  }

  @Unroll
  def 'should fail when at least one condition fails'() {
    given: 'conditions'
      def conditions = [condition1, condition2, condition3]
    and: '"and" condition receive conditions'
      testedObject = new AndCondition(conditions)

    when: '"and" condition is checked'
      final def actual = testedObject.test(context)

    then: 'first condition is always checked for truth'
      1 * condition1.test(context) >> result1
    and: 'rest of conditions is checked for truth only if required'
      numberOfInvocations2 * condition2.test(context) >> result2
      numberOfInvocations3 * condition3.test(context) >> result3
    and: 'condition is not met'
      !actual

    where:
      result1 | result2 | numberOfInvocations2 | result3 | numberOfInvocations3
      false   | false   | 0                    | false   | 0
      false   | true    | 0                    | false   | 0
      false   | false   | 0                    | true    | 0
      false   | true    | 0                    | true    | 0
      true    | false   | 1                    | false   | 0
      true    | false   | 1                    | true    | 0
      true    | true    | 1                    | false   | 1

  }

  def 'should succeed when all three conditions are true'() {
    given: 'conditions'
      def conditions = [condition1, condition2, condition3]
    and: '"and" condition receive conditions'
      testedObject = new AndCondition(conditions)

    when: '"and" condition is checked'
      final def actual = testedObject.test(context)

    then: 'all conditions are checked if they are true exactly once'
      1 * condition1.test(context) >> true
      1 * condition2.test(context) >> true
      1 * condition3.test(context) >> true
    and: 'condition is met'
      actual
  }
}