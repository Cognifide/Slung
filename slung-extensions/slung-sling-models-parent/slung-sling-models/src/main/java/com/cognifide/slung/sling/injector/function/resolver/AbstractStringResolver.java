package com.cognifide.slung.sling.injector.function.resolver;

import com.cognifide.slung.api.context.handler.converter.ObjectToStringConverter;
import java.lang.reflect.AnnotatedElement;
import java.util.Map;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.sling.models.annotations.Default;

public abstract class AbstractStringResolver implements ValueResolver {

  @Override
  public void registerIn(Map<Class<?>, ValueResolver> resolvers) {
    resolvers.put(getType(), this);
  }

  @Override
  public Object resolve(AnnotatedElement annotatedElement) {
    Object result = null;
    if (annotatedElement.isAnnotationPresent(Default.class)) {
      Default annotation = annotatedElement.getAnnotation(Default.class);
      if (ArrayUtils.isNotEmpty(annotation.values())) {
        result = directValue(annotation);
      } else {
        result = tryToCastFromPrimitiveArrays(annotation);
      }
    }
    return result;
  }

  protected abstract Object directValue(Default annotation);

  private Object tryToCastFromPrimitiveArrays(Default annotation) {
    Object result = null;
    Object array = null;
    if (ArrayUtils.isNotEmpty(annotation.booleanValues())) {
      array = annotation.booleanValues();
    } else if (ArrayUtils.isNotEmpty(annotation.doubleValues())) {
      array = annotation.doubleValues();
    } else if (ArrayUtils.isNotEmpty(annotation.floatValues())) {
      array = annotation.floatValues();
    } else if (ArrayUtils.isNotEmpty(annotation.intValues())) {
      array = annotation.intValues();
    } else if (ArrayUtils.isNotEmpty(annotation.longValues())) {
      array = annotation.longValues();
    } else if (ArrayUtils.isNotEmpty(annotation.shortValues())) {
      array = annotation.shortValues();
    }

    if (null != array) {
      result = new ObjectToStringConverter(getType(), array).produce();
    }

    return result;
  }
}
