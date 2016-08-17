package com.cognifide.slung.handler.picker.strategy;

import com.cognifide.slung.api.handler.Handler;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import java.util.Iterator;

public class Picker<T extends Handler> {

  public Optional<T> findHandlerUsing(Iterable<T> handlers, Predicate<? super T> predicate) {
    return FluentIterable.from(handlers)
        .firstMatch(predicate)
        .or(defaultFrom(handlers));
  }

  private Optional<T> defaultFrom(Iterable<T> handlers) {
    final Iterator<T> iterator = handlers.iterator();
    return iterator.hasNext() ? Optional.of(iterator.next()) : Optional.<T>absent();
  }

}
