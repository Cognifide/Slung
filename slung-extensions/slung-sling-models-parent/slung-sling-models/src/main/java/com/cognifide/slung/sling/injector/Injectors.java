package com.cognifide.slung.sling.injector;

public class Injectors {

  public static final int DIFF_CHILDREN_INJECTOR_RANKING = 1400;

  public static final String DIFF_CHILDREN_INJECTOR_NAME = "diff-children";

  public static final int DIFF_INJECTOR_RANKING = 1500;

  public static final String DIFF_INJECTOR_NAME = "diff";

  public static final int OLD_VALUE_INJECTOR_RANKING = 1600;

  public static final String OLD_VALUE_INJECTOR_NAME = "old-value";

  public static final String VALUE_MAP_INJECTOR_CLASS = "org.apache.sling.models.impl.injectors.ValueMapInjector";

  public static final String CHILD_RESOURCE_INJECTOR_CLASS = "org.apache.sling.models.impl.injectors.ChildResourceInjector";

  private Injectors() {
    throw new AssertionError();
  }
}
