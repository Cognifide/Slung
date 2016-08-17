package com.cognifide.slung.sling.injector.function.resolver;

import org.apache.sling.models.annotations.Default;

public class LongTypeResolver extends AbstractValueResolver {

  public static final ValueResolver INSTANCE = new LongTypeResolver();

  private LongTypeResolver() {
  }

  @Override
  public Class<?> getType() {
    return Long.TYPE;
  }

  @Override
  protected Object findIn(Default annotation) {
    return 0 == annotation.longValues().length ? defaultValue() : annotation.longValues()[0];
  }

  @Override
  protected Object defaultValue() {
    return 0l;
  }

}
