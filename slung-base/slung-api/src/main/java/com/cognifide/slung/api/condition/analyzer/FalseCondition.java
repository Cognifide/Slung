package com.cognifide.slung.api.condition.analyzer;

import com.cognifide.slung.condition.Condition;
import com.cognifide.slung.condition.ConditionContext;

class FalseCondition implements Condition {

  static final Condition INSTANCE = new FalseCondition();

  private FalseCondition() {
  }

  @Override
  public boolean test(ConditionContext conditionContext) {
    return false;
  }

}
