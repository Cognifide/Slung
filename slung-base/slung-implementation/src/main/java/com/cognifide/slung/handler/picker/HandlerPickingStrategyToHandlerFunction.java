package com.cognifide.slung.handler.picker;

import com.cognifide.slung.api.context.handler.HandlerContext;
import com.cognifide.slung.api.handler.Handler;
import com.cognifide.slung.api.handler.picker.HandlerPickingStrategy;
import com.google.common.base.Function;
import com.google.common.base.Optional;

public class HandlerPickingStrategyToHandlerFunction implements Function<HandlerPickingStrategy, Optional<Handler>> {

  private final HandlerContext handlerContext;

  public HandlerPickingStrategyToHandlerFunction(HandlerContext handlerContext) {
    this.handlerContext = handlerContext;
  }

  @Override
  public Optional<Handler> apply(HandlerPickingStrategy strategy) {
    return strategy.pickUsing(handlerContext);
  }

}
