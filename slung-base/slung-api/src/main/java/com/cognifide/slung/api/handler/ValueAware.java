package com.cognifide.slung.api.handler;

import com.cognifide.slung.api.strategy.RankStrategy;

public interface ValueAware extends Handler {

  RankStrategy getValueStrategy();
}
