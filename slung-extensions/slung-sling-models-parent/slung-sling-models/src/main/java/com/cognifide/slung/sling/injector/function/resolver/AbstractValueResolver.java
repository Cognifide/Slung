package com.cognifide.slung.sling.injector.function.resolver;

import java.lang.reflect.AnnotatedElement;
import java.util.Map;
import org.apache.sling.models.annotations.Default;

public abstract class AbstractValueResolver implements ValueResolver {

  @Override
  public void registerIn(Map<Class<?>, ValueResolver> resolvers) {
    resolvers.put(getType(), this);
  }

  @Override
  public Object resolve(AnnotatedElement annotatedElement) {
    return annotatedElement.isAnnotationPresent(Default.class)
        ? findIn(annotatedElement.getAnnotation(Default.class))
        : defaultValue();
  }

  protected abstract Object findIn(Default annotation);

  protected abstract Object defaultValue();
}
