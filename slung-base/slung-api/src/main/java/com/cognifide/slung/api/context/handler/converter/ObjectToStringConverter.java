package com.cognifide.slung.api.context.handler.converter;

import com.cognifide.slung.api.context.handler.ValueProducer;
import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.lang.reflect.Array;

public class ObjectToStringConverter implements ValueProducer {

  private final Class<?> type;

  private final Object array;

  private final PropertyEditor propertyEditor;

  public ObjectToStringConverter(Class<?> type, Object array) {
    this.type = type;
    this.array = array;
    this.propertyEditor = PropertyEditorManager.findEditor(String.class);
  }

  @Override
  public Object produce() {
    Object result = null;
    if (validate()) {
      result = type.isArray() ? createArray() : createSingle();
    }
    return result;
  }

  private boolean validate() {
    return null != array && 0 < Array.getLength(array);
  }

  private Object createArray() {
    final int length = Array.getLength(array);
    String[] result = new String[length];
    for (int i = 0; i < length; i++) {
      result[i] = cast(Array.get(array, i));
    }
    return result;
  }

  private String cast(Object o) {
    propertyEditor.setValue(o);
    return propertyEditor.getAsText();
  }

  private Object createSingle() {
    return cast(Array.get(array, 0));
  }

}
