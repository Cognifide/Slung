package com.cognifide.slung.api.strategy;

import com.cognifide.slung.api.strategy.result.PointsResult;
import com.cognifide.slung.api.strategy.result.RankResult;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import java.util.Collections;

public class ImmutableRankStrategy implements RankStrategy {

  private static final boolean NOT_PICKED = false;

  private static final boolean PROCEED = true;

  private final RankInformation rankInformation;

  private final Iterable<Object> values;

  private final Predicate<Object> predicate;

  public ImmutableRankStrategy(RankInformation rankInformation, Predicate<Object> predicate) {
    this(rankInformation, predicate, Collections.emptyList());
  }

  public ImmutableRankStrategy(RankInformation rankInformation, Predicate<Object> predicate, Iterable<Object> values) {
    this.rankInformation = rankInformation;
    this.predicate = predicate;
    this.values = ImmutableList.copyOf(values);
  }

  @Override
  public boolean hasToRank() {
    return rankInformation.shouldAlwaysRank();
  }

  @Override
  public RankResult defaultRank() {
    return new PointsResult(NOT_PICKED, PROCEED, hasToRank() ? 0 : rankInformation.getPoints());
  }

  @Override
  public RankResult rank(Object o) {
    boolean picked = predicate.apply(o);
    return new PointsResult(picked, rankInformation.shouldProceed(), picked ? rankInformation.getPoints() : 0);
  }

  @Override
  public Iterable<Object> getValues() {
    return values;
  }

}
