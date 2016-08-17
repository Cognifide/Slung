package com.cognifide.slung.api.configuration;

import com.cognifide.slung.api.osgi.PropertyReader;
import java.util.Map;

public abstract class AbstractCssClassesConfiguration implements CssClassesConfiguration {

  private Iterable<String> addedCssClasses;

  private Iterable<String> modifiedCssClasses;

  private Iterable<String> removedCssClasses;

  protected void activate(final Map<String, Object> configuration) {
    addedCssClasses = PropertyReader.toStrings(configuration, CssClassesProperties.ADDED_CSS_CLASSES_PROPERTY);
    modifiedCssClasses = PropertyReader.toStrings(configuration, CssClassesProperties.MODIFIED_CSS_CLASSES_PROPERTY);
    removedCssClasses = PropertyReader.toStrings(configuration, CssClassesProperties.REMOVED_CSS_CLASSES_PROPERTY);
  }

  @Override
  public Iterable<String> getAddedCssClasses() {
    return addedCssClasses;
  }

  @Override
  public Iterable<String> getModifiedCssClasses() {
    return modifiedCssClasses;
  }

  @Override
  public Iterable<String> getRemovedCssClasses() {
    return removedCssClasses;
  }

}
