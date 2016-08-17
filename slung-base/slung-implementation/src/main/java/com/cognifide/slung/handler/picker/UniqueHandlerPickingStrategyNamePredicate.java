package com.cognifide.slung.handler.picker;

import com.cognifide.slung.api.handler.picker.HandlerPickingStrategy;
import com.google.common.base.Predicate;
import com.google.common.collect.Sets;
import java.util.Set;

public class UniqueHandlerPickingStrategyNamePredicate implements Predicate<HandlerPickingStrategy> {

  private final Set<String> names;

  public UniqueHandlerPickingStrategyNamePredicate() {
    this.names = Sets.newHashSet();
  }

  @Override
  public boolean apply(HandlerPickingStrategy strategy) {
    return names.add(strategy.getName());
  }

}
