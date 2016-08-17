package com.cognifide.slung.api.util;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;

/**
 * Groups convenient functions to work with optional.
 */
public final class Optionals {

  private Optionals() {
    throw new AssertionError();
  }

  /**
   * Flattens optional. For example this fragment of code:
   * {@code Optional<String> flattened = Optionals.flatten(Optional.fromNullable(Optional.<String>fromNullable(null)));} will be equal to
   * {@link Optional#absent()}.
   *
   * @param <T> type on which optional is wrapped
   * @param optional optional of optional
   * @return if optional is wrapped around {@link Optional#absent()} then underlying {@link Optional#absent()} is returned, if optional has value then optional
   * wrapped around that value is returned.
   */
  public static <T> Optional<T> flatten(Optional<Optional<T>> optional) {
    return optional.isPresent() ? optional.get() : Optional.<T>absent();
  }

  /**
   * Executes predicate on optional only if optional is not absent and if predicate executed on optional value returns {@literal true} optional is returned,
   * {@link Optional#absent()} otherwise.
   *
   * @param <T> type on which optional is wrapped
   * @param optional optional that will be filtered
   * @param predicate predicate which will be executed on optional value
   * @return same optional as input if optional is not absent and predicate executed on optional value returns {@literal true}, {@link Optional#absent()}
   * otherwise
   */
  public static <T> Optional<T> filter(Optional<T> optional, Predicate<T> predicate) {
    return optional.isPresent() && predicate.apply(optional.get()) ? optional : Optional.<T>absent();
  }

  /**
   * Executes consumer with optional value, when optional is not absent.
   * 
   * @param <T> type on which optional is wrapped
   * @param optional optional from which value will be read for consumer
   * @param consumer code that will be executed when optional is not absent
   */
  public static <T> void ifPresent(Optional<T> optional, Consumer<T> consumer) {
    if (optional.isPresent()) {
      consumer.consume(optional.get());
    }
  }
}
