package com.cognifide.slung.api.configuration;

import com.cognifide.slung.api.Constants;

public class CssClassesProperties {

  private static final String CONFIGURATION_PREFIX = "configuration.";

  private static final String CONFIGURATION_CSS_PREFIX = CONFIGURATION_PREFIX + "css.";

  public static final String CONFIGURATION_NAME_PROPERTY = CONFIGURATION_PREFIX + "name";

  public static final String ADDED_CSS_CLASSES_LABEL = "Added css classes";

  public static final String ADDED_CSS_CLASSES_DESCRIPTION = "Css classes used when something was added according to comparison";

  public static final String ADDED_CSS_CLASSES_PROPERTY = Constants.PROPERTY_PREFIX + CONFIGURATION_CSS_PREFIX + "added";

  public static final String MODIFIED_CSS_CLASSES_LABEL = "Modified css classes";

  public static final String MODIFIED_CSS_CLASSES_DESCRIPTION = "Css classes used when something was modified according to comparison";

  public static final String MODIFIED_CSS_CLASSES_PROPERTY = Constants.PROPERTY_PREFIX + CONFIGURATION_CSS_PREFIX + "modified";

  public static final String REMOVED_CSS_CLASSES_LABEL = "Removed css classes";

  public static final String REMOVED_CSS_CLASSES_DESCRIPTION = "Css classes used when something was removed according to comparison";

  public static final String REMOVED_CSS_CLASSES_PROPERTY = Constants.PROPERTY_PREFIX + CONFIGURATION_CSS_PREFIX + "removed";

  private CssClassesProperties() {
    throw new AssertionError();
  }
}
