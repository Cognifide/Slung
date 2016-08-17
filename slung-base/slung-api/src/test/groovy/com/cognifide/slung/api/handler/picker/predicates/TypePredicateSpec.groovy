package com.cognifide.slung.api.handler.picker.predicates

import spock.lang.Specification
import spock.lang.Unroll
import com.google.common.reflect.TypeToken
import java.lang.reflect.Type

class TypePredicateSpec extends Specification {

  static final Type LIST_OF_STRINGS = new TypeToken<List<String>>() {
    private static final long serialVersionUID = 1L
  }.type

  static final Type COLLECTION_OF_STRINGS = new TypeToken<Collection<String>>() {
    private static final long serialVersionUID = 1L
  }.type

  static final Type LIST_OF_INTEGERS = new TypeToken<List<Integer>>() {
    private static final long serialVersionUID = 1L
  }.type

  TypePredicate testedObject

  @Unroll
  def "predicate is false when type #type is not assignable to handler's accepted type #handlerAcceptedType"() {
    given: 'type predicate'
      testedObject = new TypePredicate(type)

    when: 'predicate is applied'
      final def actual = testedObject.apply(handlerAcceptedType)

    then: 'predicate is false'
      !actual

    where:
      type                  | handlerAcceptedType
      int                   | String
      int[]                 | String[]
      String                | String[]
      String[]              | String
      String                | LIST_OF_STRINGS
      LIST_OF_STRINGS       | String
      String[]              | LIST_OF_STRINGS
      LIST_OF_STRINGS       | String[]
      COLLECTION_OF_STRINGS | LIST_OF_STRINGS
      LIST_OF_INTEGERS      | LIST_OF_STRINGS
      LIST_OF_STRINGS       | LIST_OF_INTEGERS
  }

  @Unroll
  def 'predicate is true when type #type is assignable to handler accepted type #handlerAcceptedType'() {
    given: 'type predicate'
      testedObject = new TypePredicate(type)

    when: 'predicate is applied'
      final def actual = testedObject.apply(handlerAcceptedType)

    then: 'predicate is true'
      actual

    where:
      type                  | handlerAcceptedType
      String                | String
      String[]              | String[]
      LIST_OF_STRINGS       | LIST_OF_STRINGS
      LIST_OF_STRINGS       | COLLECTION_OF_STRINGS
  }
}