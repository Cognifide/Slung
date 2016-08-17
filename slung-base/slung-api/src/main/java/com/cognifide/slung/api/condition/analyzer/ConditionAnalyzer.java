package com.cognifide.slung.api.condition.analyzer;

import com.cognifide.slung.api.condition.DetailedConditionContext;
import com.cognifide.slung.qualifier.ConditionGroup;
import com.google.common.collect.FluentIterable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConditionAnalyzer implements Analyzer {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConditionAnalyzer.class);

  @Override
  public boolean accepts(final DetailedConditionContext conditionContext) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("Analyzing conditions started.");
    }
    return FluentIterable.<ConditionGroup>from(conditionContext.getConditionGroups())
        .transform(ConditionGroupToPredicateFunction.INSTANCE)
        .allMatch(new ConditionPredicate(conditionContext));
  }

}
