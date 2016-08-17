package com.cognifide.slung.api.util

import com.google.common.base.Function
import spock.lang.Specification

class FunctionsSpec extends Specification {

  static final String VALUE = 'value'
  
  def 'should create function that casts from String to Integer and throws ClassCastException when used'() {
    given: 'function casting from String to Integer'
      final Function<String, Integer> function = Functions.cast(Integer)

    when: 'applied on String'
      function.apply(VALUE)

    then: 'ClassCastException is thrown'
      thrown(ClassCastException)
  }

  def 'should create function that casts from String to CharSequence'() {
    given: 'function casting from String to CharSequence'
      final Function<String, CharSequence> function = Functions.cast(CharSequence)

    when: 'applied on String'
      def actual = function.apply(VALUE)

    then: 'is still String, because String implements CharSequence'
      String == actual.class
  }
}
