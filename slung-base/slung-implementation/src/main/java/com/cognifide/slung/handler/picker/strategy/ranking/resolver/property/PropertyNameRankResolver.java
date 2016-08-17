package com.cognifide.slung.handler.picker.strategy.ranking.resolver.property;

import com.cognifide.slung.api.context.handler.HandlerContext;
import com.cognifide.slung.api.strategy.RankStrategy;
import com.cognifide.slung.handler.picker.strategy.ranking.resolver.RankResolverAdapter;
import com.cognifide.slung.api.handler.PropertyNameAware;

public class PropertyNameRankResolver extends RankResolverAdapter<PropertyNameAware> {

  public PropertyNameRankResolver(Iterable<PropertyNameAware> handlers, HandlerContext handlerContext) {
    super(handlers, handlerContext);
  }

  @Override
  protected RankStrategy strategyFrom(PropertyNameAware handler) {
    return handler.getPropertyNameStrategy();
  }

  @Override
  protected Object findValueToRank() {
    return handlerContext.getPropertyName();
  }

}
