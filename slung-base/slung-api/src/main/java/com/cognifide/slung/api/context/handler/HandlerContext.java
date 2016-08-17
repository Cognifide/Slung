package com.cognifide.slung.api.context.handler;

import com.google.common.base.Optional;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Type;
import javax.servlet.ServletRequest;
import org.apache.sling.api.resource.Resource;

public interface HandlerContext {

  AnnotatedElement getAnnotatedElement();

  String getHandlerName();

  String getPropertyName();

  Type getValueType();

  Resource getOriginalResource();

  String getResourceType();

  String getResourcePath();

  Object getOriginalValue();

  Optional<ServletRequest> findRequest();

  //@Nullable
  Resource findNewResource();

  //@Nullable
  Object findNewValue();

  //@Nullable
  Resource findOldResource();

  //@Nullable
  Object findOldValue();

}
