package com.cognifide.slung.api.handler;

import com.cognifide.slung.api.strategy.RankStrategy;

public interface PropertyNameAware extends Handler {

  RankStrategy getPropertyNameStrategy();
}
