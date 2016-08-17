package com.cognifide.slung.condition.base;

import com.cognifide.slung.condition.Condition;
import com.cognifide.slung.condition.ConditionContext;
import com.cognifide.slung.helper.DiffHelper;
import javax.annotation.Nonnull;

/**
 * Condition used in {@link ConditionGroup}. Checks if current request contains non empty "cq_diffTo" parameter.
 */
public class WithDiffParameterCondition implements Condition {

  @Override
  public boolean test(@Nonnull final ConditionContext conditionContext) {
    return DiffHelper.hasDiffParameter(conditionContext.getRequest());
  }

}
