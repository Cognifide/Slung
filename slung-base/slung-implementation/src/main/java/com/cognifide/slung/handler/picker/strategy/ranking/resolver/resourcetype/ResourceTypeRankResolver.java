package com.cognifide.slung.handler.picker.strategy.ranking.resolver.resourcetype;

import com.cognifide.slung.api.context.handler.HandlerContext;
import com.cognifide.slung.api.handler.ResourceTypeAware;
import com.cognifide.slung.api.strategy.RankStrategy;
import com.cognifide.slung.handler.picker.strategy.ranking.resolver.RankResolverAdapter;

public class ResourceTypeRankResolver extends RankResolverAdapter<ResourceTypeAware> {

  public ResourceTypeRankResolver(Iterable<ResourceTypeAware> handlers, HandlerContext handlerContext) {
    super(handlers, handlerContext);
  }

  @Override
  protected RankStrategy strategyFrom(ResourceTypeAware handler) {
    return handler.getResourceTypeStrategy();
  }

  @Override
  protected Object findValueToRank() {
    return handlerContext.getResourceType();
  }
}
