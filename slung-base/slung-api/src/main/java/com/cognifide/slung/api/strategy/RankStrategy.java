package com.cognifide.slung.api.strategy;

import com.cognifide.slung.api.strategy.result.RankResult;

public interface RankStrategy {

  boolean hasToRank();

  RankResult defaultRank();

  public RankResult rank(Object o);

  public Iterable<Object> getValues();
}
