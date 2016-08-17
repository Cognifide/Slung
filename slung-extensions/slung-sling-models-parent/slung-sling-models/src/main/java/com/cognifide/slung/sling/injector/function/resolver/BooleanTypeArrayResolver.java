package com.cognifide.slung.sling.injector.function.resolver;

import org.apache.sling.models.annotations.Default;

public class BooleanTypeArrayResolver extends AbstractValueResolver {

  public static final ValueResolver INSTANCE = new BooleanTypeArrayResolver();

  private BooleanTypeArrayResolver() {
  }

  @Override
  public Class<?> getType() {
    return boolean[].class;
  }

  @Override
  protected Object findIn(Default annotation) {
    return annotation.booleanValues();
  }

  @Override
  protected Object defaultValue() {
    return null;
  }
}
