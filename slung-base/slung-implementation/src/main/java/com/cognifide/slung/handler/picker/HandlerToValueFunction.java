package com.cognifide.slung.handler.picker;

import com.cognifide.slung.api.context.handler.HandlerContext;
import com.cognifide.slung.api.handler.Handler;
import com.google.common.base.Function;
import com.google.common.base.Optional;

public class HandlerToValueFunction implements Function<Handler, Optional<Object>> {

  private final HandlerContext handlerContext;

  public HandlerToValueFunction(HandlerContext handlerContext) {
    this.handlerContext = handlerContext;
  }

  @Override
  public Optional<Object> apply(Handler handler) {
    return Optional.fromNullable(handler.handle(handlerContext));
  }

}
