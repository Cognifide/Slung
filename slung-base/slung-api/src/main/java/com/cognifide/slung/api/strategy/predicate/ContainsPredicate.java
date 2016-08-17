package com.cognifide.slung.api.strategy.predicate;

import org.apache.commons.lang3.StringUtils;

public class ContainsPredicate extends StrategyPredicate {

  public ContainsPredicate(Iterable<Object> objects) {
    super(objects);
  }

  @Override
  protected boolean compare(CharSequence s1, CharSequence s2) {
    return StringUtils.containsIgnoreCase(s1, s2);
  }

}
