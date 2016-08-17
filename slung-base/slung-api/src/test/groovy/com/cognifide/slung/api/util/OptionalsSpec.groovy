package com.cognifide.slung.api.util

import spock.lang.Specification
import com.google.common.base.Optional
import com.google.common.base.Predicate

class OptionalsSpec extends Specification {

  static final String VALUE = 'value'

  Predicate predicate = Mock()

  Consumer consumer = Mock()

  def 'should flatten Optional of Optional of null to Optional of absent'() {
    given: 'absent optional of optional'
      Optional<Optional<String>> optional = Optional.fromNullable(Optional.<String>fromNullable(null))

    when: 'flatten is applied'
      def actual = Optionals.flatten(optional)

    then: 'returned value is absent'
      Optional.<String>absent() == actual
  }

  def 'should flatten Optional of null to Optional of absent'() {
    given: 'absent optional'
      Optional<Optional<String>> optional = Optional.fromNullable(null)

    when: 'flatten is applied'
      def actual = Optionals.flatten(optional)

    then: 'returned value is absent'
      Optional.<String>absent() == actual
  }

  def 'should flatten Optional of Optional of value to Optional of value'() {
    given: 'optional of optional with value'
      Optional<Optional<String>> optional = Optional.fromNullable(Optional.<String>fromNullable(VALUE))

    when: 'flatten is applied'
      def actual = Optionals.flatten(optional)

    then: 'optional value is "value"'
      VALUE == actual.get()
  }

  def 'should return absent Optional if absent Optional is an input without executing predicate'() {
    given: 'absent optional'
      Optional<String> optional = Optional.<String>absent()

    when: 'filter is applied'
      def actual = Optionals.filter(optional, predicate)

    then: 'predicate was not executed'
      0 * predicate._
    and: 'returned value is absent'
      Optional.<String>absent() == actual
  }

  def 'should return absent Optional when predicate was false'() {
    given: 'optional of value'
      Optional<String> optional = Optional.<String>of(VALUE)

    when: 'filter is applied'
      def actual = Optionals.filter(optional, predicate)

    then: 'predicate was executed'
      1 * predicate.apply(VALUE) >> false
    and: 'returned value is absent'
      Optional.<String>absent() == actual
  }

  def 'should return Optional when predicate was true'() {
    given: 'optional of value'
      Optional<String> optional = Optional.<String>of(VALUE)

    when: 'filter is applied'
      def actual = Optionals.filter(optional, predicate)

    then: 'predicate was executed'
      1 * predicate.apply(VALUE) >> true
    and: 'optional value is "value"'
      VALUE == actual.get()
  }

  def 'should not execute consumer when Optional is absent'() {
    given: 'absent optional'
      Optional<String> optional = Optional.<String>absent()

    when: 'attempting to execude code in consumer'
      Optionals.ifPresent(optional, consumer)

    then: 'consumer was ignored '
      0 * consumer._
  }

  def 'should execute consumer when Optional has value'() {
    given: 'absent optional'
      Optional<String> optional = Optional.<String>of(VALUE)

    when: 'attempting to execude code in consumer'
      Optionals.ifPresent(optional, consumer)

    then: 'consumer was ignored '
      1 * consumer.consume(VALUE)
  }
}