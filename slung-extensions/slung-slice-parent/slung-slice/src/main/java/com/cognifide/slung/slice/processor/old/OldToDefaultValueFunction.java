package com.cognifide.slung.slice.processor.old;

import com.cognifide.slung.qualifier.Old;
import com.google.common.base.Function;
import java.lang.reflect.Field;

public class OldToDefaultValueFunction implements Function<Field, Object> {

  public static final Function<Field, Object> INSTANCE = new OldToDefaultValueFunction();

  private OldToDefaultValueFunction() {
  }

  @Override
  public Object apply(Field field) {
    return field.getAnnotation(Old.class).defaultValue();
  }

}
