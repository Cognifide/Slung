package com.cognifide.slung.handler.picker;

import com.cognifide.slung.api.handler.picker.HandlerPickingStrategy;
import com.google.common.base.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HandlerPickingStrategyNamePredicate implements Predicate<HandlerPickingStrategy> {

  private static final Logger LOGGER = LoggerFactory.getLogger(HandlerPickingStrategyNamePredicate.class);

  private final String strategyName;

  public HandlerPickingStrategyNamePredicate(String strategyName) {
    this.strategyName = strategyName;
  }

  @Override
  public boolean apply(HandlerPickingStrategy strategy) {
    boolean result = StringUtils.equalsIgnoreCase(strategyName, strategy.getName());
    if (result && LOGGER.isDebugEnabled()) {
      LOGGER.debug("Handler picking strategy with {} name was selected.", strategyName);
    }
    return result;
  }

}
