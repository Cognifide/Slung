package com.cognifide.slung.handler.picker.strategy.tracker;

import com.cognifide.slung.api.context.handler.HandlerContext;

public interface FilteredSortedHandlerServiceTracker<T> {

  Iterable<T> findServicesFor(HandlerContext handlerContext);

  void close();
}
