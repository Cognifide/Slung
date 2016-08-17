package com.cognifide.slung.api.strategy.result;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class NoRankResult implements RankResult {

  @Override
  public boolean wasPicked() {
    return false;
  }

  @Override
  public boolean shouldProceed() {
    return true;
  }

  @Override
  public int getPoints() {
    return 0;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}
