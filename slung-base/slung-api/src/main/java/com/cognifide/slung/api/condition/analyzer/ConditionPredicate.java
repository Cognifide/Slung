package com.cognifide.slung.api.condition.analyzer;

import com.cognifide.slung.condition.Condition;
import com.cognifide.slung.condition.ConditionContext;
import com.google.common.base.Predicate;

public class ConditionPredicate implements Predicate<Condition> {

  private final ConditionContext conditionContext;

  public ConditionPredicate(ConditionContext conditionContext) {
    this.conditionContext = conditionContext;
  }

  @Override
  public boolean apply(Condition condition) {
    return condition.test(conditionContext);
  }

}
