package com.cognifide.slung.handler.picker.strategy.annotation;

import com.cognifide.slung.api.context.handler.HandlerContext;
import com.cognifide.slung.api.handler.AnnotationAware;
import com.google.common.base.Predicate;
import com.google.common.collect.Sets;
import java.lang.annotation.Annotation;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnnotationPredicate implements Predicate<AnnotationAware> {

  private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationAware.class);

  private final HandlerContext handlerContext;

  public AnnotationPredicate(HandlerContext handlerContext) {
    this.handlerContext = handlerContext;
  }

  @Override
  public boolean apply(AnnotationAware annotationAware) {
    boolean found = false;
    Set<?> annotations = Sets.newHashSet(annotationAware.getAnnotationStrategy().getValues());
    for (Annotation annotation : handlerContext.getAnnotatedElement().getAnnotations()) {
      if (annotations.contains(annotation.annotationType().getName())) {
        found = true;
        break;
      }
    }

    if (LOGGER.isDebugEnabled() && found) {
      LOGGER.debug("Handler with {} name was selected.", handlerContext.getHandlerName());
    }
    return found;
  }

}
