package com.cognifide.slung.api.configuration;

public interface CssClassesConfiguration {

  Iterable<String> getAddedCssClasses();

  Iterable<String> getModifiedCssClasses();

  Iterable<String> getRemovedCssClasses();
}
