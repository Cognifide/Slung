package com.cognifide.slung.handler.picker.strategy.ranking.resolver.name;

import com.cognifide.slung.api.context.handler.HandlerContext;
import com.cognifide.slung.api.handler.NameAware;
import com.cognifide.slung.api.strategy.RankStrategy;
import com.cognifide.slung.handler.picker.strategy.ranking.resolver.RankResolverAdapter;

public class NameRankResolver extends RankResolverAdapter<NameAware> {

  public NameRankResolver(Iterable<NameAware> handlers, HandlerContext handlerContext) {
    super(handlers, handlerContext);
  }

  @Override
  protected RankStrategy strategyFrom(NameAware handler) {
    return handler.getNameStrategy();
  }

  @Override
  protected Object findValueToRank() {
    return handlerContext.getHandlerName();
  }

}
