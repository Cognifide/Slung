package com.cognifide.slung.handler.picker.strategy.ranking.table;

import com.cognifide.slung.api.handler.Handler;
import com.cognifide.slung.api.handler.picker.strategy.ranking.resolver.RankResolverProvider;
import com.cognifide.slung.api.strategy.result.RankResult;
import java.util.Map;

public interface Ranking {

  void addColumn(RankResolverProvider<? extends Handler> rankResolverProvider, Map<? extends Handler, RankResult> column);

  boolean hasQuickerResults();

  Handler selectHandler();
}
