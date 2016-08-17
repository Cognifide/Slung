package com.cognifide.slung.api.condition.analyzer;

import com.cognifide.slung.condition.Condition;
import com.cognifide.slung.qualifier.ConditionGroup;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import java.util.Arrays;

public class ConditionGroupToPredicateFunction implements Function<ConditionGroup, Condition> {

  public static final Function<ConditionGroup, Condition> INSTANCE = new ConditionGroupToPredicateFunction();

  @Override
  public Condition apply(ConditionGroup conditionGroup) {
    return conditionGroup.conjuction().combine(toConditions(conditionGroup));
  }

  private Iterable<Condition> toConditions(ConditionGroup conditionGroup) {
    return FluentIterable.from(Arrays.asList(conditionGroup.conditions()))
        .transform(ClassToConditionFunction.INSTANCE);
  }

}
