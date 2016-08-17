package com.cognifide.slung.sling.injector.function.resolver;

import org.apache.sling.models.annotations.Default;

public class FloatTypeResolver extends AbstractValueResolver {

  public static final ValueResolver INSTANCE = new FloatTypeResolver();

  private FloatTypeResolver() {
  }

  @Override
  public Class<?> getType() {
    return Float.TYPE;
  }

  @Override
  protected Object findIn(Default annotation) {
    return 0 == annotation.floatValues().length ? defaultValue() : annotation.floatValues()[0];
  }

  @Override
  protected Object defaultValue() {
    return 0.0f;
  }

}
