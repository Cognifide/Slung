package com.cognifide.slung.sling.injector.function.resolver;

import org.apache.sling.models.annotations.Default;

public class StringArrayResolver extends AbstractStringResolver {

  public static final ValueResolver INSTANCE = new StringArrayResolver();

  private StringArrayResolver() {
  }

  @Override
  public Class<?> getType() {
    return String[].class;
  }

  @Override
  protected Object directValue(Default annotation) {
    return annotation.values();
  }

}
