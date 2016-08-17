package com.cognifide.slung.handler.picker.strategy.ranking.table;

import com.cognifide.slung.api.handler.Handler;
import com.cognifide.slung.api.handler.picker.strategy.ranking.resolver.RankResolverProvider;
import com.cognifide.slung.api.strategy.result.RankResult;
import com.cognifide.slung.handler.picker.strategy.ranking.draw.TableDraftsman;
import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TableRanking implements Board<Handler, Class<?>, Integer>, Ranking {

  private static final Logger LOGGER = LoggerFactory.getLogger(TableRanking.class);

  private final Table<Handler, Class<?>, Integer> ranking;

  private Integer maxPoints;

  private Handler selectedHandler;

  private boolean handlerWasSelectedWithoutRanking;

  public TableRanking() {
    ranking = TreeBasedTable.create(HandlerComparator.INSTANCE, ClassComparator.INSTANCE);
    selectedHandler = null;
    handlerWasSelectedWithoutRanking = false;
  }

  @Override
  public int numberOfRows() {
    return ranking.rowKeySet().size();
  }

  @Override
  public int numberOfColumns() {
    return ranking.columnKeySet().size();
  }

  @Override
  public Iterable<Handler> rows() {
    return ranking.rowKeySet();
  }

  @Override
  public Iterable<Class<?>> columns() {
    return ranking.columnKeySet();
  }

  @Override
  public Integer cell(Handler handler, Class<?> rankResolverProvider) {
    return ranking.get(handler, rankResolverProvider);
  }

  @Override
  public void addColumn(RankResolverProvider<? extends Handler> rankResolverProvider, Map<? extends Handler, RankResult> column) {
    if (!handlerWasSelectedWithoutRanking) {
      for (Map.Entry<? extends Handler, RankResult> entry : column.entrySet()) {
        final Handler handler = entry.getKey();
        final RankResult result = entry.getValue();
        canCeaseHandlerSelection(result);
        if (handlerWasSelectedWithoutRanking) {
          select(handler);
        } else {
          countPointsAndSelect(handler, rankResolverProvider, result);
        }
      }
    }
  }

  private void canCeaseHandlerSelection(final RankResult rankResult) {
    handlerWasSelectedWithoutRanking = rankResult.wasPicked() && !rankResult.shouldProceed();
  }

  private void select(final Handler handler) {
    selectedHandler = handler;
  }

  private void countPointsAndSelect(final Handler handler, final RankResolverProvider<? extends Handler> rankResolverProvider, final RankResult result) {
    int points = result.getPoints();
    ranking.put(handler, rankResolverProvider.getClass(), points);
    if (!ranking.contains(handler, Sum.class)) {
      ranking.put(handler, Sum.class, points);
    } else {
      points = ranking.get(handler, Sum.class) + points;
      ranking.put(handler, Sum.class, points);
    }

    if (canSelect(handler, points)) {
      select(handler);
      maxPoints = points;
    }
  }

  private boolean canSelect(final Handler handler, final Integer points) {
    return hasMorePoints(points) || hasBetterName(handler, points);
  }

  private boolean hasMorePoints(final Integer points) {
    return maxPoints == null || points.compareTo(maxPoints) > 0;
  }

  private boolean hasBetterName(final Handler handler, final Integer points) {
    return points.equals(maxPoints) && (null == selectedHandler || handler.getName().compareTo(selectedHandler.getName()) > 0);
  }

  @Override
  public boolean hasQuickerResults() {
    return handlerWasSelectedWithoutRanking;
  }

  @Override
  public Handler selectHandler() {
    logSelection();
    return selectedHandler;
  }

  private void logSelection() {
    if (LOGGER.isDebugEnabled()) {
      if (null == selectedHandler) {
        LOGGER.debug("Handler was not selected.");
      } else if (handlerWasSelectedWithoutRanking) {
        LOGGER.debug("Handler '{}' with handler name '{}' was selected without counting points", selectedHandler.getClass(), selectedHandler.getName());
      } else {
        LOGGER.debug("Handler '{}' with handler name '{}' was selected having '{}' points", new Object[]{selectedHandler.getClass(), selectedHandler.getName(), ranking.get(selectedHandler, Sum.class)});
      }
    }
  }

  @Override
  public String toString() {
    return ranking.isEmpty() ? StringUtils.EMPTY : new TableDraftsman(this).draw();
  }

}
