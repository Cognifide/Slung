package com.cognifide.slung.api.handler.picker.strategy.ranking.resolver;

import com.cognifide.slung.api.handler.Handler;
import com.cognifide.slung.api.strategy.result.RankResult;
import java.util.Map;

public interface RankResolver<T extends Handler> {

  Map<T, RankResult> rank();
}
