package com.cognifide.slung.api.strategy.predicate;

import com.google.common.base.Predicate;
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.Objects;

abstract class StrategyPredicate implements Predicate<Object> {

  private final Iterable<Object> objects;

  StrategyPredicate(Iterable<Object> objects) {
    this.objects = Objects.requireNonNull(objects);
  }

  @Override
  public boolean apply(Object o) {
    return isArray(o) ? compareArray(o) : o instanceof Iterable ? compareIterable(o) : compare(o);
  }

  private boolean isArray(Object o) {
    return o.getClass().isArray();
  }

  private boolean compareArray(Object array) {
    boolean result = false;

    final int length = Array.getLength(array);
    for (int i = 0; i < length && !result; i++) {
      result = compare(Array.get(array, i));
    }

    return result;
  }

  private boolean compare(Object o) {
    boolean result = false;
    boolean isCharSequence = isCharSequence(o);
    for (Object object : objects) {
      if (Objects.equals(object, o) || isCharSequence && isCharSequence(object) && compare((CharSequence) o, (CharSequence) object)) {
        result = true;
        break;
      }
    }
    return result;
  }

  private boolean isCharSequence(Object o) {
    return CharSequence.class.isAssignableFrom(o.getClass());
  }

  protected abstract boolean compare(CharSequence s1, CharSequence s2);

  private boolean compareIterable(Object iterable) {
    boolean result = false;
    Iterator<?> iterator = ((Iterable) iterable).iterator();
    while (iterator.hasNext() && !result) {
      result = compare(iterator.next());
    }
    return result;
  }

}
