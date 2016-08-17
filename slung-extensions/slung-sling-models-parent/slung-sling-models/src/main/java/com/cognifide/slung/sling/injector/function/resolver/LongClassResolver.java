package com.cognifide.slung.sling.injector.function.resolver;

import org.apache.sling.models.annotations.Default;

public class LongClassResolver extends AbstractValueResolver {

  public static final ValueResolver INSTANCE = new LongClassResolver();

  private LongClassResolver() {
  }

  @Override
  public Class<?> getType() {
    return Long.class;
  }

  @Override
  protected Object findIn(Default annotation) {
    return 0 == annotation.longValues().length ? defaultValue() : annotation.longValues()[0];
  }

  @Override
  protected Object defaultValue() {
    return null;
  }

}
