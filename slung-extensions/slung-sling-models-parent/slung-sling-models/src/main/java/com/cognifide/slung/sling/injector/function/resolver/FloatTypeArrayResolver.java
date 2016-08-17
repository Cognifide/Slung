package com.cognifide.slung.sling.injector.function.resolver;

import org.apache.sling.models.annotations.Default;

public class FloatTypeArrayResolver extends AbstractValueResolver {

  public static final ValueResolver INSTANCE = new FloatTypeArrayResolver();

  private FloatTypeArrayResolver() {
  }

  @Override
  public Class<?> getType() {
    return float[].class;
  }

  @Override
  protected Object findIn(Default annotation) {
    return annotation.floatValues();
  }

  @Override
  protected Object defaultValue() {
    return null;
  }

}
