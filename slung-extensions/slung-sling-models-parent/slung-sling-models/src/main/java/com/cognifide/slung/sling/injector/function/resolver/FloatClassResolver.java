package com.cognifide.slung.sling.injector.function.resolver;

import org.apache.sling.models.annotations.Default;

public class FloatClassResolver extends AbstractValueResolver {

  public static final ValueResolver INSTANCE = new FloatClassResolver();
  
  private FloatClassResolver() {
  }
  
  @Override
  public Class<?> getType() {
    return Float.class;
  }
  
  @Override
  protected Object findIn(Default annotation) {
    return 0 == annotation.floatValues().length ? defaultValue() : annotation.floatValues()[0];
  }

  @Override
  protected Object defaultValue() {
    return null;
  }


}
