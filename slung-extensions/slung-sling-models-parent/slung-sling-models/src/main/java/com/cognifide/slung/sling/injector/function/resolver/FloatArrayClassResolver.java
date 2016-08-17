package com.cognifide.slung.sling.injector.function.resolver;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.sling.models.annotations.Default;

public class FloatArrayClassResolver extends AbstractValueResolver {

  public static final ValueResolver INSTANCE = new FloatArrayClassResolver();

  private FloatArrayClassResolver() {
  }

  @Override
  public Class<?> getType() {
    return Float[].class;
  }

  @Override
  protected Object findIn(Default annotation) {
    return ArrayUtils.toObject(annotation.floatValues());
  }

  @Override
  protected Object defaultValue() {
    return null;
  }

}
