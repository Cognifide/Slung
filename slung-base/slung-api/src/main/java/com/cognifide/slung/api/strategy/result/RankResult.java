package com.cognifide.slung.api.strategy.result;

public interface RankResult {

  boolean wasPicked();

  boolean shouldProceed();

  int getPoints();

}
