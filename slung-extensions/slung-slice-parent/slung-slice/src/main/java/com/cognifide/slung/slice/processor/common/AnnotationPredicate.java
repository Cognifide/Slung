package com.cognifide.slung.slice.processor.common;

import com.google.common.base.Predicate;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

class AnnotationPredicate implements Predicate<Class<? extends Annotation>> {

  private final Field field;

  AnnotationPredicate(Field field) {
    this.field = field;
  }

  @Override
  public boolean apply(Class<? extends Annotation> annotation) {
    return field.isAnnotationPresent(annotation);
  }

}
