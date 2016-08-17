package com.cognifide.slung.api.handler.picker;

import com.cognifide.slung.api.context.handler.HandlerContext;

public interface HandlerPicker {

  //@Nullable
  Object tryToProcessUsing(HandlerContext handlerContext);
}
