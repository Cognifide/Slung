package com.cognifide.slung.api.context.handler;

import com.cognifide.slung.api.context.handler.seeker.OldResourceSeeker;
import com.cognifide.slung.qualifier.Diff;
import com.google.common.base.Preconditions;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import javax.servlet.ServletRequest;
import org.apache.jackrabbit.JcrConstants;
import org.apache.sling.api.resource.Resource;

public class HandlerContextBuilder {

  private final ServletRequest request;

  private Resource resource;

  private String propertyName;

  private AnnotatedElement annotatedElement;

  private String handlerName;

  private Type valueType;

  private Object value;

  private ValueProducer valueProducer;

  private HandlerContextBuilder(ServletRequest request) {
    this.request = request;
  }

  public HandlerContextBuilder resource(Resource currentResource) {
    this.resource = currentResource;
    return this;
  }

  public HandlerContextBuilder propertyName(String propertyName) {
    this.propertyName = propertyName;
    return this;
  }

  public HandlerContextBuilder field(Field field) {
    this.annotatedElement = Preconditions.checkNotNull(field);
    this.handlerName = field.getAnnotation(Diff.class).handlerName();
    this.valueType = field.getGenericType();
    return this;
  }

  public HandlerContextBuilder annotatedElement(AnnotatedElement annotatedElement) {
    this.annotatedElement = annotatedElement;
    return this;
  }

  public HandlerContextBuilder handlerName(String handlerName) {
    this.handlerName = handlerName;
    return this;
  }

  public HandlerContextBuilder valueType(Type valueType) {
    this.valueType = valueType;
    return this;
  }

  public HandlerContextBuilder value(Object value) {
    this.value = value;
    return this;
  }

  public HandlerContextBuilder valueProducer(ValueProducer valueProducer) {
    this.valueProducer = valueProducer;
    return this;
  }

  public HandlerContext build() {
    final Resource oldResource = OldResourceSeeker.using(request, resource).seek().orNull();
    final Object oldValue = findValueIn(oldResource);
    final boolean reverseResources = shouldReverseResources(oldResource);

    return new ImmutableHandlerContext(request, annotatedElement, handlerName, propertyName, valueType,
        resource, useDefaultIfNull(value), oldResource, useDefaultIfNull(oldValue), reverseResources);
  }

  private Object findValueIn(Resource resource) {
    return null == resource ? null : resource.getValueMap().get(propertyName);
  }

  private boolean shouldReverseResources(Resource oldResource) {
    return null == oldResource && isResourceFrozen();
  }

  private boolean isResourceFrozen() {
    return null != resource
        && JcrConstants.NT_FROZENNODE.equals(resource.getValueMap().get(JcrConstants.JCR_PRIMARYTYPE, String.class));
  }

  private Object useDefaultIfNull(Object value) {
    return null == value ? valueProducer.produce() : value;
  }

  public static HandlerContextBuilder from(ServletRequest request) {
    return new HandlerContextBuilder(request);
  }
}
