package com.cognifide.slung.handler.picker.strategy.ranking.resolver.value;

import com.cognifide.slung.api.context.handler.HandlerContext;
import com.cognifide.slung.api.strategy.RankStrategy;
import com.cognifide.slung.handler.picker.strategy.ranking.resolver.RankResolverAdapter;
import com.cognifide.slung.api.handler.ValueAware;

public class ValueRankResolver extends RankResolverAdapter<ValueAware> {

  public ValueRankResolver(Iterable<ValueAware> handlers, HandlerContext handlerContext) {
    super(handlers, handlerContext);
  }

  @Override
  protected RankStrategy strategyFrom(ValueAware handler) {
    return handler.getValueStrategy();
  }

  @Override
  protected Object findValueToRank() {
    return handlerContext.getOriginalValue();
  }

}
