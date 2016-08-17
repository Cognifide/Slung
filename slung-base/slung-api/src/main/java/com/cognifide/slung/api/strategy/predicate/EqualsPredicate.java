package com.cognifide.slung.api.strategy.predicate;

import org.apache.commons.lang3.StringUtils;

public class EqualsPredicate extends StrategyPredicate {

  public EqualsPredicate(Iterable<Object> objects) {
    super(objects);
  }

  @Override
  protected boolean compare(CharSequence s1, CharSequence s2) {
    return StringUtils.equalsIgnoreCase(s1, s2);
  }
}
