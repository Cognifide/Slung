package com.cognifide.slung.handler.picker.strategy.ranking.table;

import com.google.common.base.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RankingSupplier implements Supplier<Ranking> {

  private static final Logger LOGGER = LoggerFactory.getLogger(RankingSupplier.class);
  
  public static final Supplier<Ranking> INSTANCE = new RankingSupplier();

  @Override
  public Ranking get() {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("Creating new ranking.");
    }
    return new TableRanking();
  }

}
