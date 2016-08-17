package com.cognifide.slung.condition;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

/**
 * Describes predicate. All classes implementing this interface must be immutable.
 */
@Immutable
public interface Condition {

  /**
   * Checks whether given condition is true or false.
   *
   * @param conditionContext non null condition context
   * @return {@code true} if condition is met, {@code false} otherwise
   */
  boolean test(@Nonnull ConditionContext conditionContext);
}
