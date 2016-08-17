package com.cognifide.slung.api.handler.picker.strategy.ranking.resolver;

import com.cognifide.slung.api.context.handler.HandlerContext;
import com.cognifide.slung.api.handler.Handler;

public interface RankResolverProvider<T extends Handler> {

  boolean isActive();

  boolean resultCanBeCached();

  RankResolver<T> provide(HandlerContext handlerContext);
}
