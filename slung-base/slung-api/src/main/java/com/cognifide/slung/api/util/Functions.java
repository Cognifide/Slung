package com.cognifide.slung.api.util;

import com.google.common.base.Function;


/**
 * Groups convenient functions to work with function.
 */
public final class Functions {

  private Functions() {
    throw new AssertionError();
  }

  /**
   * Returns a function that always returns the result of invoking cast on input.
   * 
   * @param <K> input parameter
   * @param <V> output parameter, type to which input will be cast
   * @param type class which {@link Class#cast(java.lang.Object)} method will be invoked on input parameter
   * @return input casted to type's class
   */
  public static <K, V> Function<K, V> cast(final Class<? extends V> type) {
    return new Function<K, V>() {
      @Override
      public V apply(K k) {
        return type.cast(k);
      }
    };
  }
}
