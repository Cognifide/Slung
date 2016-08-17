package com.cognifide.slung.condition.aggregate;

import com.cognifide.slung.condition.Condition;
import com.cognifide.slung.condition.ConditionContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Joins together multiple conditions with "or" conjunction. If there are no conditions then the result is
 * {@code false}. Conditions are executed one after another. If one of them responds {@code true}, execution is stopped
 * and result {@code true} is returned. Rest of conditions are omitted in that situation. If all conditions respond
 * {@code false} then {@code false} is returned.
 */
public class OrCondition implements Condition {

  private static final Logger LOGGER = LoggerFactory.getLogger(OrCondition.class);

  private static final int PREDICTED_MAX_NUMBER_OF_CONDITIONS = 8;

  private final Iterable<Condition> conditions;

  public OrCondition(@Nonnull Iterable<Condition> conditions) {
    Objects.requireNonNull(conditions);
    this.conditions = copy(conditions);
  }

  private Iterable<Condition> copy(final Iterable<Condition> elements) {
    final List<Condition> result = new ArrayList<>(PREDICTED_MAX_NUMBER_OF_CONDITIONS);
    for (Condition element : elements) {
      result.add(element);
    }
    return result;
  }

  @Override
  public boolean test(@Nonnull final ConditionContext conditionContext) {
    boolean result = false;
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("Conditions below have 'or' conjuction.");
    }
    for (Condition condition : conditions) {
      final boolean apply = condition.test(conditionContext);
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("{} condition result {}", condition.getClass().getName(), apply);
      }
      if (apply) {
        result = true;
        break;
      }
    }
    return result;
  }

}
