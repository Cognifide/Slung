package com.cognifide.slung.handler.picker.strategy.ranking.resolver;

import com.cognifide.slung.api.handler.Handler;
import com.cognifide.slung.api.handler.picker.strategy.ranking.resolver.RankResolverProvider;
import com.google.common.base.Predicate;

public class ActiveRankResolverProviderPredicate implements Predicate<RankResolverProvider<? extends Handler>> {

  public static final Predicate<RankResolverProvider<? extends Handler>> INSTANCE = new ActiveRankResolverProviderPredicate();

  @Override
  public boolean apply(RankResolverProvider<? extends Handler> rankResolverProvider) {
    return rankResolverProvider.isActive();
  }

}
