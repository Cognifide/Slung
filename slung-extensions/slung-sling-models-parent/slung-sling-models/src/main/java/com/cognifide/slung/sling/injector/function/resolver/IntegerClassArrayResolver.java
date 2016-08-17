package com.cognifide.slung.sling.injector.function.resolver;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.sling.models.annotations.Default;

public class IntegerClassArrayResolver extends AbstractValueResolver {

  public static final ValueResolver INSTANCE = new IntegerClassArrayResolver();

  private IntegerClassArrayResolver() {
  }

  @Override
  public Class<?> getType() {
    return Integer[].class;
  }

  @Override
  protected Object findIn(Default annotation) {
    return ArrayUtils.toObject(annotation.intValues());
  }

  @Override
  protected Object defaultValue() {
    return null;
  }

}
