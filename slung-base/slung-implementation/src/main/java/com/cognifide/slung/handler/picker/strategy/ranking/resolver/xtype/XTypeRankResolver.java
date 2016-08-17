package com.cognifide.slung.handler.picker.strategy.ranking.resolver.xtype;

import com.cognifide.slung.api.context.handler.HandlerContext;
import com.cognifide.slung.api.handler.XTypeAware;
import com.cognifide.slung.api.strategy.RankStrategy;
import com.cognifide.slung.handler.picker.strategy.ranking.resolver.RankResolverAdapter;
import com.cognifide.slung.handler.picker.strategy.ranking.resolver.xtype.cache.TypeCache;

public class XTypeRankResolver extends RankResolverAdapter<XTypeAware> {

  private final TypeCache typeCache;

  public XTypeRankResolver(Iterable<XTypeAware> handlers, HandlerContext handlerContext, TypeCache typeCache) {
    super(handlers, handlerContext);
    this.typeCache = typeCache;
  }

  @Override
  protected RankStrategy strategyFrom(XTypeAware handler) {
    return handler.getXTypeStrategy();
  }

  @Override
  protected Object findValueToRank() {
    return typeCache.getOrPut(handlerContext.getOriginalResource(), handlerContext.getPropertyName());
  }
}
