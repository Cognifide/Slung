package com.cognifide.slung.api.util;

/**
 * Accepts single value and returns no result.
 * 
 * @param <T> type for input
 */
public interface Consumer<T> {

  /**
   * Performs operation on given input
   * @param t
   */
  void consume(T t);
}
