package com.cognifide.slung.sling.injector.function.resolver;

import org.apache.sling.models.annotations.Default;

public class ShortClassResolver extends AbstractValueResolver {

  public static final ValueResolver INSTANCE = new ShortClassResolver();

  private ShortClassResolver() {
  }

  @Override
  public Class<?> getType() {
    return Short.class;
  }

  @Override
  protected Object findIn(Default annotation) {
    return 0 == annotation.shortValues().length ? defaultValue() : annotation.shortValues()[0];
  }

  @Override
  protected Object defaultValue() {
    return null;
  }

}
