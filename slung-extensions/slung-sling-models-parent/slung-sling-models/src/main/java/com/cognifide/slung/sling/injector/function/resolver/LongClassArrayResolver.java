package com.cognifide.slung.sling.injector.function.resolver;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.sling.models.annotations.Default;

public class LongClassArrayResolver extends AbstractValueResolver {

  public static final ValueResolver INSTANCE = new LongClassArrayResolver();

  private LongClassArrayResolver() {
  }

  @Override
  public Class<?> getType() {
    return Long[].class;
  }

  @Override
  protected Object findIn(Default annotation) {
    return ArrayUtils.toObject(annotation.longValues());
  }

  @Override
  protected Object defaultValue() {
    return null;
  }

}
