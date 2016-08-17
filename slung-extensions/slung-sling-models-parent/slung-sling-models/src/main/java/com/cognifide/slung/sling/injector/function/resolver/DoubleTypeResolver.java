package com.cognifide.slung.sling.injector.function.resolver;

import org.apache.sling.models.annotations.Default;

public class DoubleTypeResolver extends AbstractValueResolver {

  public static final ValueResolver INSTANCE = new DoubleTypeResolver();

  private DoubleTypeResolver() {
  }

  @Override
  public Class<?> getType() {
    return double.class;
  }

  @Override
  protected Object findIn(Default annotation) {
    return 0 == annotation.doubleValues().length ? defaultValue() : annotation.doubleValues()[0];
  }

  @Override
  protected Object defaultValue() {
    return 0.0d;
  }

}
