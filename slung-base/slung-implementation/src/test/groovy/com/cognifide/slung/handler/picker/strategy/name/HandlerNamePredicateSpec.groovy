package com.cognifide.slung.handler.picker.strategy.name

import com.cognifide.slung.api.context.handler.HandlerContext
import com.cognifide.slung.api.handler.Handler
import spock.lang.Specification

class HandlerNamePredicateSpec extends Specification {

  static final String HANDLER_NAME = 'name'

  Handler handler = Mock()

  HandlerContext handlerContext = Mock()

  HandlerNamePredicate testedObject

  def 'should not meet condition when handler name #name is different from "name"'() {
    given: 'handler context'
      handlerContext.handlerName >> HANDLER_NAME
    and: 'handler'
      handler.name >> name
    and: 'handler name predicate'
      testedObject = new HandlerNamePredicate(handlerContext)

    when: 'predicate is applied'
      final def actual = testedObject.apply(handler)

    then: 'condition is not meet'
      !actual

    where:
      name << [null, '', 'a']
  }

  def 'should meet condition when handler name #name is "name"'() {
    given: 'handler context'
      handlerContext.handlerName >> HANDLER_NAME
    and: 'handler'
      handler.name >> HANDLER_NAME
    and: 'handler name predicate'
      testedObject = new HandlerNamePredicate(handlerContext)

    when: 'predicate is applied'
      final def actual = testedObject.apply(handler)

    then: 'condition is not meet'
      actual
  }
}
