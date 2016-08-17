package com.cognifide.slung.api.condition;

import com.cognifide.slung.condition.ConditionContext;
import com.cognifide.slung.qualifier.ConditionGroup;

public interface DetailedConditionContext extends ConditionContext {

  Iterable<ConditionGroup> getConditionGroups();
}
