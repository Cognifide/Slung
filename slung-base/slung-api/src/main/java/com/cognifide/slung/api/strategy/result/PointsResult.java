package com.cognifide.slung.api.strategy.result;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class PointsResult implements RankResult {

  private final  boolean picked;

  private final boolean proceed;

  private final int points;

  public PointsResult(boolean picked, boolean proceed, int points) {
    this.picked = picked;
    this.proceed = proceed;
    this.points = points;
  }

  @Override
  public boolean wasPicked() {
    return picked;
  }

  @Override
  public boolean shouldProceed() {
    return proceed;
  }

  @Override
  public int getPoints() {
    return points;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}
