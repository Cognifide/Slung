package com.cognifide.slung.handler.picker.strategy.ranking.table;

public interface Board<R, C, V> {

  int numberOfRows();

  int numberOfColumns();

  Iterable<R> rows();

  Iterable<C> columns();

  V cell(R r, C c);
}
