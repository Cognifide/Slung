package com.cognifide.slung.api.handler.picker.predicates

import com.cognifide.slung.api.handler.Handler
import spock.lang.Specification
import spock.lang.Unroll

class UniqueHandlerNamePredicateSpec extends Specification {

  static final String HANDLER_NAME = 'name'

  Handler handler = Mock()

  UniqueHandlerNamePredicate testedObject

  @Unroll
  def 'predicate throws illegal argument exception when handler name is null, empty or blank'() {
    given: 'handler'
      handler.name >> name
    and: 'unique handler name predicate'
      testedObject = new UniqueHandlerNamePredicate()

    when: 'predicate is applied'
      final def actual = testedObject.apply(handler)

    then: 'illegal argument exception is trown with handler class name in message'
      IllegalArgumentException x = thrown()
      x.message ==~ /Found\s.+?\swith\sempty\sname\./

    where:
      name << [null, '', '  ']
  }

  def 'predicate is not valid when there is a handler with the same name (appear before)'() {
    given: 'handler'
      handler.name >> HANDLER_NAME
    and: 'unique handler name predicate with processed handler'
      testedObject = new UniqueHandlerNamePredicate()
      testedObject.apply(handler(HANDLER_NAME))

    when: 'processing handler with duplicate name'
      final def actual = testedObject.apply(handler)

    then: 'predicate is false'
      !actual
  }

  @Unroll
  def 'predicate is valid only when name is unique (did not appear before)'() {
    given: 'handler'
      handler.name >> name
    and: 'unique handler name predicate with processed handlers'
      testedObject = new UniqueHandlerNamePredicate()
      processedHandlers.each {
        testedObject.apply(it)
      }

    when: 'processing handler'
      final def actual = testedObject.apply(handler)

    then: 'predicate is true'
      actual

    where:
      processedHandlers            | name
      []                           | 'a'
      [handler('a'), handler('b')] | 'c'
  }

  def handler(final String name) {
    [getName: {-> name}] as Handler
  }
}