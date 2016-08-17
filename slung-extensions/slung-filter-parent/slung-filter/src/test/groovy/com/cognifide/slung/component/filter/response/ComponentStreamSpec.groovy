package com.cognifide.slung.component.filter.response

import spock.lang.Specification

class ComponentStreamSpec extends Specification {

  static final int INTEGER = 100

  static final byte[] BYTE_ARRAY = [0, 0, 0] as byte[]

  static final int OFFSET = 50

  static final int LENGTH = 50

  OutputStream stream = Mock()

  ComponentStream testedObject

  def 'component stream should pass integer to output stream underneath'() {
    given: 'component stream'
      testedObject = new ComponentStream(stream)

    when: 'integer is written to component stream'
      testedObject.write(INTEGER)

    then: 'integer is written to output stream underneath'
      1 * stream.write(INTEGER)
      0 * _
  }

  def 'component stream should pass byte array to output stream underneath'() {
    given: 'component stream'
      OutputStream stream = Mock()
      testedObject = new ComponentStream(stream)

    when: 'byte array is written to component stream'
      testedObject.write(BYTE_ARRAY)

    then: 'byte array is written to output stream underneath'
      1 * stream.write(BYTE_ARRAY)
      0 * _
  }

  def 'component stream should pass byte array with delimiters to output stream underneath'() {
    given: 'component stream'
      testedObject = new ComponentStream(stream)

    when: 'byte array with delimiters is written to component stream'
      testedObject.write(BYTE_ARRAY, OFFSET, LENGTH)

    then: 'byte array with delimiters is written to output stream underneath'
      1 * stream.write(BYTE_ARRAY, OFFSET, LENGTH)
      0 * _
  }
}