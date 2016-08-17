package com.cognifide.slung.api.osgi

import spock.lang.Specification
import spock.lang.Unroll

class PropertyReaderSpec extends Specification {

  static final String PROPERTY_NAME = 'property'

  def 'should return false when there is no boolean property in configuration'() {
    when: 'property is read from empty configuration'
      final def actual = PropertyReader.toBoolean([:], PROPERTY_NAME)
    then: 'property value is false'
      !actual
  }

  def 'should return #expected when there is boolean property set to #value in configuration'() {
    given: 'configuration'
      def configuration = [(PROPERTY_NAME): value]
    when: 'property is read from configuration'
      final def actual = PropertyReader.toBoolean(configuration, PROPERTY_NAME)
    then: 'property value is expected boolean'
      expected == actual
    where:
      value || expected
      false || false
      true  || true
  }

  def 'should return zero when there is no integer property in configuration'() {
    when: 'property is read from empty configuration'
      final def actual = PropertyReader.toInteger([:], PROPERTY_NAME)
    then: 'property value is zero'
      0 == actual
  }

  @Unroll
  def 'should return #expected when there is integer property set to #value in configuration'() {
    given: 'configuration'
      def configuration = [(PROPERTY_NAME): value]
    when: 'property is read from configuration'
      final def actual = PropertyReader.toInteger(configuration, PROPERTY_NAME)
    then: 'property value is expected integer'
      expected == actual
    where:
      value || expected
      0     || 0
      1     || 1
      10000 || 10000
  }

  def 'should return empty string when there is no string property in configuration'() {
    when: 'property is read from empty configuration'
      final def actual = PropertyReader.toString([:], PROPERTY_NAME)
    then: 'property value is empty string'
      '' == actual
  }

  @Unroll
  def 'should return #expected when there is string property set to #value in configuration'() {
    given: 'configuration'
      def configuration = [(PROPERTY_NAME): value]
    when: 'property is read from configuration'
      final def actual = PropertyReader.toString(configuration, PROPERTY_NAME)
    then: 'property value is expected string'
      expected == actual
    where:
      value   || expected
      ''      || ''
      'a'     || 'a'
      '10000' || '10000'
  }

  def 'should return empty iterable when there are no string property values in configuration'() {
    when: 'property is read from empty configuration'
      final def actual = PropertyReader.toStrings([:], PROPERTY_NAME)
    then: 'property value is empty iterable'
      actual.empty
  }

  @Unroll
  def 'should return #expected when there is property with string array type with values #values in configuration'() {
    given: 'configuration'
      def configuration = [(PROPERTY_NAME): values]
    when: 'property is read from configuration'
      final def actual = PropertyReader.toStrings(configuration, PROPERTY_NAME)
    then: 'property value is expected string'
      expected == actual
    where:
      values                          || expected
      [] as String[]                  || [] as Set
      [] as Integer[]                 || [] as Set
      'a'                             || ['a'] as Set
      ['a'] as String[]               || ['a'] as Set
      ['a', 'b', 'c'] as String[]     || ['a', 'b', 'c'] as Set
  }
}
