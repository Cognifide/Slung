package com.cognifide.slung.api.context.handler;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Type;
import javax.servlet.ServletRequest;
import org.apache.sling.api.resource.Resource;

class ImmutableHandlerContext implements HandlerContext {

  private final ServletRequest request;

  private final AnnotatedElement annotatedElement;

  private final String handlerName;

  private final String propertyName;

  private final Type valueType;

  private final Object originalValue;

  private final Resource originalResource;

  private final String resourceType;

  private final String path;

  private final Resource oldResource;

  private final Object oldValue;

  private final boolean reverseResources;

  ImmutableHandlerContext(ServletRequest request, AnnotatedElement annotatedElement, String handlerName, String propertyName, Type valueType,
      Resource originalResource, Object originalValue, Resource oldResource, Object oldValue, boolean reverseResources) {
    this.request = request;
    this.annotatedElement = Preconditions.checkNotNull(annotatedElement);
    this.handlerName = handlerName;
    this.propertyName = Preconditions.checkNotNull(propertyName);
    this.valueType = Preconditions.checkNotNull(valueType);
    this.originalResource = Preconditions.checkNotNull(originalResource);
    this.resourceType = this.originalResource.getResourceType();
    this.path = this.originalResource.getPath();
    this.originalValue = originalValue;
    this.oldResource = oldResource;
    this.oldValue = oldValue;
    this.reverseResources = reverseResources;
  }

  @Override
  public AnnotatedElement getAnnotatedElement() {
    return annotatedElement;
  }

  @Override
  public String getHandlerName() {
    return handlerName;
  }

  @Override
  public String getPropertyName() {
    return propertyName;
  }

  @Override
  public Type getValueType() {
    return valueType;
  }

  @Override
  public Resource getOriginalResource() {
    return originalResource;
  }

  @Override
  public String getResourceType() {
    return resourceType;
  }

  @Override
  public String getResourcePath() {
    return path;
  }

  @Override
  public Object getOriginalValue() {
    return originalValue;
  }

  @Override
  public Optional<ServletRequest> findRequest() {
    return Optional.fromNullable(request);
  }

  @Override
  public Resource findNewResource() {
    return reverseResources ? oldResource : originalResource;
  }

  @Override
  public Object findNewValue() {
    return reverseResources ? oldValue : originalValue;
  }

  @Override
  public Resource findOldResource() {
    return reverseResources ? originalResource : oldResource;
  }

  @Override
  public Object findOldValue() {
    return reverseResources ? originalValue : oldValue;
  }
}
