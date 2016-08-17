package com.cognifide.slung.sling.injector.function.resolver;

import org.apache.sling.models.annotations.Default;

public class BooleanTypeResolver extends AbstractValueResolver {

  public static final ValueResolver INSTANCE = new BooleanTypeResolver();

  private BooleanTypeResolver() {
  }

  @Override
  public Class<?> getType() {
    return Boolean.TYPE;
  }

  @Override
  protected Object findIn(Default annotation) {
    return 0 == annotation.booleanValues().length ? defaultValue() : annotation.booleanValues()[0];
  }

  @Override
  protected Object defaultValue() {
    return false;
  }
}
