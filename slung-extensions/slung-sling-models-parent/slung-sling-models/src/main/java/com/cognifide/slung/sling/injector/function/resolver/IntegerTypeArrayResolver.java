package com.cognifide.slung.sling.injector.function.resolver;

import org.apache.sling.models.annotations.Default;

public class IntegerTypeArrayResolver extends AbstractValueResolver {

  public static final ValueResolver INSTANCE = new IntegerTypeArrayResolver();

  private IntegerTypeArrayResolver() {
  }

  @Override
  public Class<?> getType() {
    return int[].class;
  }

  @Override
  protected Object findIn(Default annotation) {
    return annotation.intValues();
  }

  @Override
  protected Object defaultValue() {
    return null;
  }

}
