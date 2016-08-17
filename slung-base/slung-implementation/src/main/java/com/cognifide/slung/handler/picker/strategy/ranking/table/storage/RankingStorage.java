package com.cognifide.slung.handler.picker.strategy.ranking.table.storage;

import com.cognifide.slung.handler.picker.strategy.ranking.table.Ranking;
import com.google.common.base.Supplier;

public interface RankingStorage {

  Ranking retriveOrCreate(Supplier<Ranking> supplier);

  Ranking store(Ranking ranking);

  boolean wasStored();

}
