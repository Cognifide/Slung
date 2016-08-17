package com.cognifide.slung.api.handler.picker;

import com.cognifide.slung.api.Constants;
import com.cognifide.slung.api.context.handler.HandlerContext;
import com.cognifide.slung.api.handler.Handler;
import com.cognifide.slung.api.handler.picker.predicates.ActiveHandlerPredicate;
import com.cognifide.slung.api.handler.picker.predicates.HandlerTypePredicate;
import com.cognifide.slung.api.handler.picker.predicates.UniqueHandlerNamePredicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import java.util.List;

public class HandlerPickingStrategies {

  public static final String HANDLER_PICKING_STRATEGY_NAME_LABEL = "Handler picking strategy name";

  public static final String HANDLER_PICKING_STRATEGY_NAME_DESCRIPTION = "Allows to select handler picking strategy by name";

  public static final String HANDLER_PICKING_STRATEGY_NAME_PROPERTY = Constants.PROPERTY_PREFIX + "strategy.picking.name";

  private HandlerPickingStrategies() {
    throw new AssertionError();
  }

  public static <T extends Handler> Iterable<T> filter(final HandlerContext handlerContext, final List<T> handlers) {
    return Iterables.filter(validate(handlers), Predicates.and(ActiveHandlerPredicate.INSTANCE, new HandlerTypePredicate(handlerContext)));
  }

  private static <T extends Handler> Iterable<T> validate(final Iterable<T> handlers) {
    if (!containsUnique(handlers)) {
      throw new IllegalStateException("There is a name conflict among handlers. At least two handlers have the same name.");
    }
    return handlers;
  }

  private static <T extends Handler> boolean containsUnique(final Iterable<T> handlers) {
    return Iterables.all(handlers, new UniqueHandlerNamePredicate());
  }
}
