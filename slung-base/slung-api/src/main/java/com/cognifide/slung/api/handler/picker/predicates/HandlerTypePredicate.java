package com.cognifide.slung.api.handler.picker.predicates;

import com.cognifide.slung.api.context.handler.HandlerContext;
import com.cognifide.slung.api.handler.Handler;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import java.lang.reflect.Type;

public class HandlerTypePredicate implements Predicate<Handler> {

  private final Predicate<Type> typePredicate;

  public HandlerTypePredicate(HandlerContext handlerContext) {
    this.typePredicate = new TypePredicate(handlerContext.getValueType());
  }

  @Override
  public boolean apply(Handler handler) {
    return Iterables.any(handler.getAcceptedTypes(), typePredicate);
  }

}
