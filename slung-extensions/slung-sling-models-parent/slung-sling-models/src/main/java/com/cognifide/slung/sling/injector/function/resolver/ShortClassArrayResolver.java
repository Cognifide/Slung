package com.cognifide.slung.sling.injector.function.resolver;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.sling.models.annotations.Default;

public class ShortClassArrayResolver extends AbstractValueResolver {

  public static final ValueResolver INSTANCE = new ShortClassArrayResolver();

  private ShortClassArrayResolver() {
  }

  @Override
  public Class<?> getType() {
    return Short[].class;
  }

  @Override
  protected Object findIn(Default annotation) {
    return ArrayUtils.toObject(annotation.shortValues());
  }

  @Override
  protected Object defaultValue() {
    return null;
  }

}
