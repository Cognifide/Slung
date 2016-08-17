package com.cognifide.slung.sling.injector.function.resolver;

import org.apache.sling.models.annotations.Default;

public class ShortTypeArrayResolver extends AbstractValueResolver {

  public static final ValueResolver INSTANCE = new ShortTypeArrayResolver();

  private ShortTypeArrayResolver() {
  }

  @Override
  public Class<?> getType() {
    return short[].class;
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
