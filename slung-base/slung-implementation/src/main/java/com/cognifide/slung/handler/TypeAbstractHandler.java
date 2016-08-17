package com.cognifide.slung.handler;

import com.cognifide.slung.api.context.handler.HandlerContext;
import com.cognifide.slung.api.transformer.Transformers;
import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import org.apache.commons.lang3.reflect.TypeUtils;

public abstract class TypeAbstractHandler<T> extends AbstractHandler {

  private Transformers<T> transformers;

  @Override
  protected void activate(Map<String, Object> configuration) {
    super.activate(configuration);
    transformers = new Transformers<>();
  }

  @Override
  public Object handle(HandlerContext handlerContext) {
    final Class<?> returnType = TypeUtils.getRawType(handlerContext.getValueType(), null);
    return Collection.class.isAssignableFrom(returnType)
        ? handleCollection(handlerContext) : returnType.isArray()
        ? handleArray(handlerContext)
        : handleSingle(handlerContext);
  }

  private T handleSingle(HandlerContext handlerContext) {
    return diff(handlerContext.findNewValue(), handlerContext.findOldValue());
  }

  private T[] handleArray(HandlerContext handlerContext) {
    return createArray(handlerContext.getValueType(), handlerContext.findNewValue(), handlerContext.findOldValue());
  }

  private T[] createArray(final Type type, final Object o1, final Object o2) {
    @SuppressWarnings("unchecked")
    final Class<T> componentType = ((Class) type).getComponentType();

    final T[] array1 = objectToArray(componentType, o1);
    final T[] array2 = objectToArray(componentType, o2);

    return diffArrays(componentType, array1, array2);
  }

  @SuppressWarnings("unchecked")
  private T[] objectToArray(final Class<T> type, final Object o) {
    return null == o ? (T[]) Array.newInstance(type, 0) : (o.getClass().isArray() ? (T[]) o : createArrayWithSingleElement(type, o));
  }

  @SuppressWarnings("unchecked")
  private T[] createArrayWithSingleElement(final Class<T> componentType, final Object o) {
    T[] array = (T[]) Array.newInstance(componentType, 1);
    array[0] = (T) o;
    return array;
  }

  private T[] diffArrays(final Class<T> componentType, final T[] array1, final T[] array2) {
    final int length = Math.max(array1.length, array2.length);

    @SuppressWarnings("unchecked")
    final T[] array = (T[]) Array.newInstance(componentType, length);
    for (int i = 0; i < length; i++) {
      array[i] = diff(orNull(array1, i), orNull(array2, i));
    }
    return array;
  }

  private T orNull(final T[] array, final int index) {
    return index < array.length ? array[index] : null;
  }

  private Collection<T> handleCollection(HandlerContext handlerContext) {
    Type[] types = ((ParameterizedType) handlerContext.getValueType()).getActualTypeArguments();
    @SuppressWarnings("unchecked")
    final Class<T> componentType = (Class) types[0];

    final T[] array1 = collectionToArray(componentType, handlerContext.findNewValue());
    final T[] array2 = collectionToArray(componentType, handlerContext.findOldValue());

    return toCollection(handlerContext, diffArrays(componentType, array1, array2));
  }

  @SuppressWarnings("unchecked")
  private T[] collectionToArray(final Class<T> componentType, final Object o) {
    return o != null && Collection.class.isAssignableFrom(TypeUtils.getRawType(o.getClass(), null)) ? (T[]) ((Collection) o).toArray() : objectToArray(componentType, o);
  }

  private Collection<T> toCollection(final HandlerContext handlerContext, final T[] array) {
    return transformers.transform(collectionType(handlerContext), Arrays.asList(array));
  }

  private Class<?> collectionType(final HandlerContext handlerContext) {
    final Class<?> type = seekClass(handlerContext.findNewValue());
    return null == type ? seekClass(handlerContext.findOldValue()) : type;
  }

  private Class<?> seekClass(final Object o) {
    return null == o ? null : o.getClass();
  }

  protected abstract T diff(final Object newValue, final Object oldValue);

}
