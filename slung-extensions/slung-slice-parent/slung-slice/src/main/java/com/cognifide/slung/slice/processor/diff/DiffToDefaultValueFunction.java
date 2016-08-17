package com.cognifide.slung.slice.processor.diff;

import com.cognifide.slung.qualifier.Diff;
import com.google.common.base.Function;
import java.lang.reflect.Field;

public class DiffToDefaultValueFunction implements Function<Field, Object> {

  public static final Function<Field, Object> INSTANCE = new DiffToDefaultValueFunction();
  
  private DiffToDefaultValueFunction() {
  }
  
  @Override
  public Object apply(Field field) {
    return field.getAnnotation(Diff.class).defaultValue();
  }
  
}
