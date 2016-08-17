package com.cognifide.slung.api.handler.picker;

import com.cognifide.slung.api.context.handler.HandlerContext;
import com.cognifide.slung.api.handler.Handler;
import com.google.common.base.Optional;

public interface HandlerPickingStrategy {

  String getName();

  Optional<Handler> pickUsing(HandlerContext handlerContext);
}
