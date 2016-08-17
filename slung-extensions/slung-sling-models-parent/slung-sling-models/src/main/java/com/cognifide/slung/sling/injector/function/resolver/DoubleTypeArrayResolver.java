package com.cognifide.slung.sling.injector.function.resolver;

import org.apache.sling.models.annotations.Default;

public class DoubleTypeArrayResolver extends AbstractValueResolver {

  public static final ValueResolver INSTANCE = new DoubleTypeArrayResolver();

  private DoubleTypeArrayResolver() {
  }

  @Override
  public Class<?> getType() {
    return double[].class;
  }

  @Override
  protected Object findIn(Default annotation) {
    return annotation.doubleValues();
  }

  @Override
  protected Object defaultValue() {
    return null;
  }

}
