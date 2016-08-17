package com.cognifide.slung.handler.picker.strategy.annotation

import com.cognifide.slung.api.context.handler.HandlerContext
import com.cognifide.slung.api.handler.AnnotationAware
import com.cognifide.slung.api.strategy.RankStrategy
import java.lang.reflect.AnnotatedElement
import spock.lang.Ignore
import spock.lang.Specification
import spock.lang.Unroll

class AnnotationPredicateSpec extends Specification {

  AnnotationAware annotationAware = Mock()

  RankStrategy annotationStrategy = Mock()

  HandlerContext handlerContext = Mock()

  AnnotatedElement annotatedElement = Mock()

  AnnotationPredicate testedObject

  @Unroll
  def 'should not meet condition when annotated element does not have annotation from annotation strategy'() {
    given: 'annotation aware handler'
      setUpHandler(values)
    and: 'handler context'
      setUpHandlerContext(annotations)
    and: 'annotation predicate'
      testedObject = new AnnotationPredicate(handlerContext)

    when: 'predicate is applied'
      final def actual = testedObject.apply(annotationAware)

    then: 'condition is not meet'
      !actual

    where:
      values                                 | annotations
      []                                     | []
      []                                     | [toAnnotation(String)]
      []                                     | [toAnnotation(String), toAnnotation(Integer), toAnnotation(Long)]
      [String.name]                          | []
      [String.name, Integer.name, Long.name] | []
  }

  def setUpHandler(values) {
    annotationAware.annotationStrategy >> annotationStrategy
    annotationStrategy.values >> values
  }

  def setUpHandlerContext(annotations) {
    handlerContext.annotatedElement >> annotatedElement
    annotatedElement.annotations >> annotations
  }

  def toAnnotation(Class<?> type) {
    [annotationType : {type}] as java.lang.annotation.Annotation
  }

  @Unroll
  def 'should meet condition when annotated element has annotation form annotation strategy'() {
    given: 'annotation aware handler'
      setUpHandler(values)
    and: 'handler context'
      setUpHandlerContext(annotations)
    and: 'annotation predicate'
      testedObject = new AnnotationPredicate(handlerContext)

    when: 'predicate is applied'
      final def actual = testedObject.apply(annotationAware)

    then: 'condition is meet'
      actual

    where:
      values                                 | annotations
      [Integer.name, Long.name, String.name] | [toAnnotation(String)]
      [String.name]                          | [toAnnotation(Integer), toAnnotation(String), toAnnotation(Long)]
      [Byte.name, Short.name, String.name]   | [toAnnotation(Integer), toAnnotation(String), toAnnotation(Long)]
  }
}
