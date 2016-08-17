package com.cognifide.slung.handler.picker.strategy.ranking.resolver;

import com.cognifide.slung.api.context.handler.HandlerContext;
import com.cognifide.slung.api.handler.Handler;
import com.cognifide.slung.api.handler.picker.strategy.ranking.resolver.RankResolver;
import com.cognifide.slung.api.strategy.RankStrategy;
import com.cognifide.slung.api.strategy.result.RankResult;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import java.util.Map;

public abstract class RankResolverAdapter<T extends Handler> implements RankResolver<T> {

  private final Iterable<T> handlers;

  protected final HandlerContext handlerContext;

  public RankResolverAdapter(Iterable<T> handlers, HandlerContext handlerContext) {
    this.handlers = Preconditions.checkNotNull(handlers);
    this.handlerContext = Preconditions.checkNotNull(handlerContext);
  }

  @Override
  public Map<T, RankResult> rank() {
    final Map<T, RankResult> results = Maps.newHashMap();
    final Object valueToRank = findValueToRank();
    for (T handler : handlers) {
      final RankStrategy strategy = strategyFrom(handler);
      results.put(handler, strategy.hasToRank() ? strategy.rank(valueToRank) : strategy.defaultRank());
    }
    return results;
  }

  protected abstract RankStrategy strategyFrom(T handler);

  protected abstract Object findValueToRank();
}
