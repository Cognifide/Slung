package com.cognifide.slung.handler.picker.strategy.ranking.table.storage;

import com.cognifide.slung.handler.picker.strategy.ranking.table.Ranking;
import com.google.common.base.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ObjectToRankingFunction implements Function<Object, Ranking> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ObjectToRankingFunction.class);
  
  public static final Function<Object, Ranking> INSTANCE = new ObjectToRankingFunction();

  @Override
  public Ranking apply(Object o) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("Reusing stored ranking in request.");
    }
    return (Ranking) o;
  }

}
