package com.cognifide.slung.sling.injector.function.resolver;

import org.apache.sling.models.annotations.Default;

public class StringResolver extends AbstractStringResolver {

  public static final ValueResolver INSTANCE = new StringResolver();

  private StringResolver() {
  }

  @Override
  public Class<?> getType() {
    return String.class;
  }

  @Override
  protected Object directValue(Default annotation) {
    return annotation.values()[0];
  }

}
