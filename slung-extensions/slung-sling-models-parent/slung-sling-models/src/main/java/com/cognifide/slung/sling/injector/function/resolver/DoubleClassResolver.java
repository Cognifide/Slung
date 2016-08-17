package com.cognifide.slung.sling.injector.function.resolver;

import org.apache.sling.models.annotations.Default;

public class DoubleClassResolver extends AbstractValueResolver {

  public static final ValueResolver INSTANCE = new DoubleClassResolver();

  private DoubleClassResolver() {
  }

  @Override
  public Class<?> getType() {
    return Double.class;
  }

  @Override
  protected Object findIn(Default annotation) {
    return 0 == annotation.doubleValues().length ? defaultValue() : annotation.doubleValues()[0];
  }

  @Override
  protected Object defaultValue() {
    return null;
  }

}
