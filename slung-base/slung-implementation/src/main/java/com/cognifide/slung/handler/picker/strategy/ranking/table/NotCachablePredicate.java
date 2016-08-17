package com.cognifide.slung.handler.picker.strategy.ranking.table;

import com.cognifide.slung.api.handler.picker.strategy.ranking.resolver.RankResolverProvider;
import com.google.common.base.Predicate;

public class NotCachablePredicate implements Predicate<RankResolverProvider<?>> {

  public static final Predicate<RankResolverProvider<?>> INSTANCE = new NotCachablePredicate();

  @Override
  public boolean apply(RankResolverProvider<?> input) {
    return !input.resultCanBeCached();
  }

}
