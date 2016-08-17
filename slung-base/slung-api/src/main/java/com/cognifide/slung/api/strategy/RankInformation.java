package com.cognifide.slung.api.strategy;

public class RankInformation {

  public static final RankInformation SIGNIFICANT = new RankInformation(false, true, 100);

  public static final RankInformation UNSIGNIFICANT = new RankInformation(true, true, 50);

  public static final RankInformation ANY = new RankInformation(true, false, 10);

  public static final RankInformation SKIP = new RankInformation(true, false, 0);

  private final boolean proceed;

  private final boolean alwaysRank;

  private final int points;

  public RankInformation(boolean proceed, boolean alwaysRank, int points) {
    this.proceed = proceed;
    this.alwaysRank = alwaysRank;
    this.points = points;
  }

  public boolean shouldProceed() {
    return proceed;
  }

  public boolean shouldAlwaysRank() {
    return alwaysRank;
  }

  public int getPoints() {
    return points;
  }

}
