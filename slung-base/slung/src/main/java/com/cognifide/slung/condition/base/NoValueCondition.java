package com.cognifide.slung.condition.base;

import com.cognifide.slung.condition.Condition;
import com.cognifide.slung.condition.ConditionContext;
import java.lang.reflect.Array;
import javax.annotation.Nonnull;

/**
 * Condition used in {@link ConditionGroup}. Checks if given field that is inspected does not have value and default
 * value set (If default value is an array it is checked whether array is empty.
 */
public class NoValueCondition implements Condition {

  @Override
  public boolean test(@Nonnull final ConditionContext conditionContext) {
    return null == conditionContext.getValue() && noDefaultValue(conditionContext);
  }

  private boolean noDefaultValue(ConditionContext conditionContext) {
    final Object defaultValue = conditionContext.getDefaultValue();
    return null == defaultValue
        || (defaultValue.getClass().isArray() && 0 == Array.getLength(defaultValue));
  }

}
