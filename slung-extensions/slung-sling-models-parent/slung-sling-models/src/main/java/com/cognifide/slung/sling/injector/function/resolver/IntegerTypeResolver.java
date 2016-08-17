package com.cognifide.slung.sling.injector.function.resolver;

import org.apache.sling.models.annotations.Default;

public class IntegerTypeResolver extends AbstractValueResolver {

  public static final ValueResolver INSTANCE = new IntegerTypeResolver();

  private IntegerTypeResolver() {
  }

  @Override
  public Class<?> getType() {
    return Integer.TYPE;
  }

  @Override
  protected Object findIn(Default annotation) {
    return 0 == annotation.intValues().length ? defaultValue() : annotation.intValues()[0];
  }

  @Override
  protected Object defaultValue() {
    return 0;
  }

}
