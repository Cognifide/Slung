package com.cognifide.slung.handler.picker.strategy.ranking.resolver.annotation;

import com.cognifide.slung.api.context.handler.HandlerContext;
import com.cognifide.slung.api.handler.AnnotationAware;
import com.cognifide.slung.api.strategy.RankStrategy;
import com.cognifide.slung.handler.picker.strategy.ranking.resolver.RankResolverAdapter;
import com.google.common.collect.Sets;
import java.lang.annotation.Annotation;
import java.util.Set;

public class AnnotationRankResolver extends RankResolverAdapter<AnnotationAware> {

  public AnnotationRankResolver(Iterable<AnnotationAware> handlers, HandlerContext handlerContext) {
    super(handlers, handlerContext);
  }

  @Override
  protected RankStrategy strategyFrom(AnnotationAware handler) {
    return handler.getAnnotationStrategy();
  }

  @Override
  protected Object findValueToRank() {
    Set<String> types = Sets.newHashSet();
    for (Annotation annotation : handlerContext.getAnnotatedElement().getDeclaredAnnotations()) {
      types.add(annotation.annotationType().getName());
    }
    return types;
  }

}
