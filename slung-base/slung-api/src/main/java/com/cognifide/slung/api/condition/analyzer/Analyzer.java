package com.cognifide.slung.api.condition.analyzer;

import com.cognifide.slung.api.condition.DetailedConditionContext;

public interface Analyzer {

  boolean accepts(DetailedConditionContext conditionContext);
}
