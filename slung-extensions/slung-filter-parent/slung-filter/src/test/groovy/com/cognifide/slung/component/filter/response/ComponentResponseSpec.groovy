package com.cognifide.slung.component.filter.response

import javax.servlet.http.HttpServletResponse
import spock.lang.Specification

class ComponentResponseSpec extends Specification {

  HttpServletResponse response = Mock()

  ComponentResponse testedObject

  def 'output stream should not be null'() {
    given: 'component response'
      testedObject = new ComponentResponse(response)

    when: 'asked for output stream'
      final def actual = testedObject.getOutputStream()

    then: 'stream was created'
      null != actual
  }

  def 'writer should not be null'() {
    given: 'component response'
      response.getCharacterEncoding() >> 'utf8'
      testedObject = new ComponentResponse(response)

    when: 'asked for writer'
      final def actual = testedObject.getWriter()

    then: 'print writer was created'
      null != actual
  }

  def 'content should not be null'() {
    given: 'component response'
      testedObject = new ComponentResponse(response)

    when: 'asked for content'
      final def actual = testedObject.getContent()

    then: 'content is not null'
      null != actual
    and: 'content size is 0'
      0 == actual.size()
  }
}

