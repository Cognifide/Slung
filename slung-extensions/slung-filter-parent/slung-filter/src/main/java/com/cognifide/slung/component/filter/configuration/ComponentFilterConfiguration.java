package com.cognifide.slung.component.filter.configuration;

public interface ComponentFilterConfiguration {

  boolean isEnabled();

  boolean canFilter(String resourceType);
}
