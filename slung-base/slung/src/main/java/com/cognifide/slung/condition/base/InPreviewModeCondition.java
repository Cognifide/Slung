package com.cognifide.slung.condition.base;

import com.cognifide.slung.condition.Condition;
import com.cognifide.slung.condition.ConditionContext;
import com.cognifide.slung.helper.DiffHelper;
import com.cognifide.slung.qualifier.ConditionGroup;
import javax.annotation.Nonnull;

/**
 * Condition used in {@link ConditionGroup}. Checks if current request is in preview mode.
 */
public class InPreviewModeCondition implements Condition {

  @Override
  public boolean test(@Nonnull final ConditionContext conditionContext) {
    return DiffHelper.isPreviewMode(conditionContext.getRequest());
  }

}
