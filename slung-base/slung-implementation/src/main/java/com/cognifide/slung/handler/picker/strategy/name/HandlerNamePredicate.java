package com.cognifide.slung.handler.picker.strategy.name;

import com.cognifide.slung.api.context.handler.HandlerContext;
import com.cognifide.slung.api.handler.Handler;
import com.google.common.base.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HandlerNamePredicate implements Predicate<Handler> {

  private static final Logger LOGGER = LoggerFactory.getLogger(HandlerNamePredicate.class);

  private final HandlerContext handlerContext;

  public HandlerNamePredicate(HandlerContext handlerContext) {
    this.handlerContext = handlerContext;
  }

  @Override
  public boolean apply(Handler handler) {
    boolean result = StringUtils.equalsIgnoreCase(handlerContext.getHandlerName(), handler.getName());
    if (result && LOGGER.isDebugEnabled()) {
      LOGGER.debug("Handler with {} name was selected.", handlerContext.getHandlerName());
    }
    return result;
  }

}
