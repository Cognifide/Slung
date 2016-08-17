package com.cognifide.slung.api.handler;

import com.cognifide.slung.api.context.handler.HandlerContext;
import java.lang.reflect.Type;

public interface Handler {

  boolean isActive();

  Iterable<Type> getAcceptedTypes();

  String getName();

  Object handle(HandlerContext handlerContext);
}
