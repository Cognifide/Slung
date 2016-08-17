package com.cognifide.slung.sling.injector.collector;

import com.google.common.base.Predicate;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

public class AnnotationPredicate implements Predicate<Class<? extends Annotation>> {

  private final AnnotatedElement annotatedElement;

  public AnnotationPredicate(AnnotatedElement annotatedElement) {
    this.annotatedElement = annotatedElement;
  }

  @Override
  public boolean apply(Class<? extends Annotation> annotation) {
    return annotatedElement.isAnnotationPresent(annotation);

  }

}
