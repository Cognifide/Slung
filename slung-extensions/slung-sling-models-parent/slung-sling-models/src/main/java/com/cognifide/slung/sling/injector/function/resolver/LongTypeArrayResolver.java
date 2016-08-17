package com.cognifide.slung.sling.injector.function.resolver;

import org.apache.sling.models.annotations.Default;

public class LongTypeArrayResolver extends AbstractValueResolver {

  public static final ValueResolver INSTANCE = new LongTypeArrayResolver();

  private LongTypeArrayResolver() {
  }

  @Override
  public Class<?> getType() {
    return long[].class;
  }

  @Override
  protected Object findIn(Default annotation) {
    return annotation.longValues();
  }

  @Override
  protected Object defaultValue() {
    return null;
  }

}
