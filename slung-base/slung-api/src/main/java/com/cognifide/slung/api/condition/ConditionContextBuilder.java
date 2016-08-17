package com.cognifide.slung.api.condition;

import com.cognifide.slung.qualifier.ConditionGroup;
import java.lang.reflect.AnnotatedElement;
import javax.servlet.ServletRequest;
import org.apache.sling.api.resource.Resource;

public class ConditionContextBuilder<R> {

  private ServletRequest request;

  private Resource resource;

  private AnnotatedElement annotatedElement;

  private Object value;

  private Object defaultValue;

  private Iterable<ConditionGroup> conditionGroups;

  public static ConditionContextBuilder<Void> from(ServletRequest request) {
    return new ConditionContextBuilder<>().request(request);
  }

  @SuppressWarnings("unchecked")
  private ConditionContextBuilder<Void> request(ServletRequest request) {
    this.request = request;
    return (ConditionContextBuilder<Void>) this;
  }

  public ConditionContextBuilder<R> resource(Resource resource) {
    this.resource = resource;
    return this;
  }

  public ConditionContextBuilder<R> annotatedElement(AnnotatedElement annotatedElement) {
    this.annotatedElement = annotatedElement;
    return this;
  }

  public ConditionContextBuilder<R> value(Object value) {
    this.value = value;
    return this;
  }

  public ConditionContextBuilder<R> defaultValue(Object defaultValue) {
    this.defaultValue = defaultValue;
    return this;
  }

  @SuppressWarnings("unchecked")
  public ConditionContextBuilder<DetailedConditionContext> conditionGroups(Iterable<ConditionGroup> conditionGroups) {
    this.conditionGroups = conditionGroups;
    return (ConditionContextBuilder<DetailedConditionContext>) this;
  }

  @SuppressWarnings("unchecked")
  public R build() {
//  public DetailedConditionContext build() {
    return (R)new ImmutableConditionContext(request, resource, annotatedElement, value, defaultValue, conditionGroups);
  }
}
