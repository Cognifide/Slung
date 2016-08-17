package com.cognifide.slung.handler.picker.strategy.ranking.table;

import com.cognifide.slung.api.context.handler.HandlerContext;
import com.cognifide.slung.api.handler.Handler;
import com.cognifide.slung.api.handler.picker.strategy.ranking.resolver.RankResolverProvider;
import com.cognifide.slung.handler.picker.strategy.ranking.table.storage.RankingStorage;
import com.cognifide.slung.handler.picker.strategy.ranking.table.storage.RequestRankingStorage;
import com.google.common.collect.Iterables;
import java.util.Iterator;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RankingProvider {

  private static final Logger LOGGER = LoggerFactory.getLogger(RankingProvider.class);
  
  private final HandlerContext handlerContext;

  private final RankingStorage rankingStorage;

  private final Iterable<RankResolverProvider<? extends Handler>> rankResolverProviders;

  private Ranking ranking;

  public RankingProvider(HandlerContext handlerContext, Iterable<RankResolverProvider<? extends Handler>> rankResolverProviders) {
    this.handlerContext = Objects.requireNonNull(handlerContext);
    this.rankingStorage = new RequestRankingStorage(handlerContext);
    this.rankResolverProviders = rankResolverProviders;
  }

  public Ranking provide() {
    ranking = rankingStorage.retriveOrCreate(RankingSupplier.INSTANCE);

    for (Iterator<RankResolverProvider<? extends Handler>> iterator = rankResolverProvidersIterator(); !ranking.hasQuickerResults() && iterator.hasNext();) {
      RankResolverProvider<? extends Handler> rankResolverProvider = iterator.next();
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("Providing ranking with {}", rankResolverProvider);
      }
      ranking.addColumn(rankResolverProvider, rankResolverProvider.provide(handlerContext).rank());
    }

    return rankingStorage.store(ranking);
  }

  private Iterator<RankResolverProvider<? extends Handler>> rankResolverProvidersIterator() {
    return rankingStorage.wasStored()
        ? Iterables.filter(rankResolverProviders, NotCachablePredicate.INSTANCE).iterator()
        : rankResolverProviders.iterator();
  }
}
