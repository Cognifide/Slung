package com.cognifide.slung.api.condition;

import com.cognifide.slung.qualifier.ConditionGroup;
import com.google.common.collect.ImmutableList;
import java.lang.reflect.AnnotatedElement;
import java.util.Objects;
import javax.annotation.CheckForNull;
import javax.servlet.ServletRequest;
import org.apache.sling.api.resource.Resource;

class ImmutableConditionContext implements DetailedConditionContext {

  private final ServletRequest request;

  private final Resource resource;

  private final AnnotatedElement annotatedElement;

  private final Object value;

  private final Object defaultValue;

  private final Iterable<ConditionGroup> conditionGroups;

  ImmutableConditionContext(ServletRequest request, Resource resource, AnnotatedElement annotatedElement, Object value, Object defaultValue, Iterable<ConditionGroup> conditionGroups) {
    this.request = request;
    this.resource = Objects.requireNonNull(resource);
    this.annotatedElement = Objects.requireNonNull(annotatedElement);
    this.value = value;
    this.defaultValue = defaultValue;
    this.conditionGroups = ImmutableList.copyOf(Objects.requireNonNull(conditionGroups));
  }

  @CheckForNull
  @Override
  public ServletRequest getRequest() {
    return request;
  }

  @Override
  public Resource getResource() {
    return resource;
  }

  @Override
  public AnnotatedElement getAnnotatedElement() {
    return annotatedElement;
  }

  @CheckForNull
  @Override
  public Object getValue() {
    return value;
  }

  @CheckForNull
  @Override
  public Object getDefaultValue() {
    return defaultValue;
  }

  @Override
  public Iterable<ConditionGroup> getConditionGroups() {
    return conditionGroups;
  }

}
