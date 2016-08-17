package com.cognifide.slung.condition;

import java.lang.reflect.AnnotatedElement;
import javax.annotation.CheckForNull;
import javax.servlet.ServletRequest;
import org.apache.sling.api.resource.Resource;

/**
 * Context used by conditions. Based on current request, resource for which model is created and model's field.
 */
public interface ConditionContext {

  /**
   * Current request.
   *
   * @return request, might be null
   */
  @CheckForNull
  ServletRequest getRequest();

  /**
   * Current resource.
   *
   * @return resource
   */
  Resource getResource();

  /**
   * Annotated element from field for which computation is made.
   *
   * @return annotated element
   */
  AnnotatedElement getAnnotatedElement();

  /**
   * Computed field's value.
   *
   * @return value, might be null
   */
  @CheckForNull
  Object getValue();

  /**
   * Computed field's default value.
   *
   * @return default value, might be null
   */
  @CheckForNull
  Object getDefaultValue();
}
