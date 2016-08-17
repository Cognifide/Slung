package com.cognifide.slung.slice.processor.common;

import com.cognifide.slice.mapper.annotation.JcrProperty;
import com.cognifide.slung.qualifier.Diff;
import com.cognifide.slung.qualifier.Old;
import java.lang.reflect.Field;
import org.apache.commons.lang3.StringUtils;

public class PropertyNameFinder {

  private PropertyNameFinder() {
    throw new AssertionError();
  }

  public static String find(final Field field) {
    return StringUtils.defaultIfBlank(StringUtils.defaultIfBlank(fromAnnotation(field), field.getAnnotation(JcrProperty.class).value()), field.getName());
  }

  private static String fromAnnotation(final Field field) {
    String result = field.isAnnotationPresent(Diff.class) ? field.getAnnotation(Diff.class).propertyName() : null;
    return null == result && field.isAnnotationPresent(Old.class) ? field.getAnnotation(Old.class).propertyName() : result;
  }
}
