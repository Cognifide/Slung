package com.cognifide.slung.api.context.handler.converter;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Arrays;
import com.cognifide.slung.api.context.handler.ValueProducer;

public class StringToObjectConverter implements ValueProducer {

  private final Class<?> type;

  private final String[] values;

  private PropertyEditor propertyEditor;

  public StringToObjectConverter(Field field, String[] values) {
    this.type = field.getType();
    this.values = Arrays.copyOf(values, values.length);
  }

  @Override
  public Object produce() {
    Object result = null;
    if (validate()) {
      determinePropertyEditor();
      result = type.isArray() ? createArray() : createSingle();
    }
    return result;
  }

  private boolean validate() {
    return null != type && null != values && 0 < values.length;
  }

  private void determinePropertyEditor() {
    Class<?> targetType = type.isArray() ? type.getComponentType() : type;
    propertyEditor = PropertyEditorManager.findEditor(targetType);
  }

  private Object createArray() {
    Object array = Array.newInstance(type.getComponentType(), values.length);
    for (int i = 0; i < values.length; i++) {
      Array.set(array, i, cast(values[i]));
    }
    return array;
  }

  private Object cast(String s) {
    Object result = null;
    if (null != propertyEditor) {
      propertyEditor.setAsText(s);
      result = propertyEditor.getValue();
    }
    return result;
  }

  private Object createSingle() {
    return cast(values[0]);
  }

}
