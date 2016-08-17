package com.cognifide.slung.example.condition;

import com.cognifide.slung.condition.Condition;
import com.cognifide.slung.condition.ConditionContext;

public class RedCondition implements Condition {

  private static final String RED = "red";

  @Override
  public boolean test(ConditionContext conditionContext) {
    Object value = conditionContext.getValue();
    return null != value && String.class.isAssignableFrom(value.getClass()) && RED.equalsIgnoreCase((String) value);
  }

}
