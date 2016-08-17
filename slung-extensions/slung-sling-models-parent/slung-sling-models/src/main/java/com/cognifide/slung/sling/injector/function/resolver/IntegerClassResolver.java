package com.cognifide.slung.sling.injector.function.resolver;

import org.apache.sling.models.annotations.Default;

public class IntegerClassResolver extends AbstractValueResolver {

  public static final ValueResolver INSTANCE = new IntegerClassResolver();

  private IntegerClassResolver() {
  }

  @Override
  public Class<?> getType() {
    return Integer.class;
  }

  @Override
  protected Object findIn(Default annotation) {
    return 0 == annotation.intValues().length ? defaultValue() : annotation.intValues()[0];
  }

  @Override
  protected Object defaultValue() {
    return null;
  }

}
