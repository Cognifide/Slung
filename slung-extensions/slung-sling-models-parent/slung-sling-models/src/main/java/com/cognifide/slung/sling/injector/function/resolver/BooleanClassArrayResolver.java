package com.cognifide.slung.sling.injector.function.resolver;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.sling.models.annotations.Default;

public class BooleanClassArrayResolver extends AbstractValueResolver {

  public static final ValueResolver INSTANCE = new BooleanClassArrayResolver();

  private BooleanClassArrayResolver() {
  }

  @Override
  public Class<?> getType() {
    return Boolean[].class;
  }

  @Override
  protected Object findIn(Default annotation) {
    return ArrayUtils.toObject(annotation.booleanValues());
  }

  @Override
  protected Object defaultValue() {
    return null;
  }
}
