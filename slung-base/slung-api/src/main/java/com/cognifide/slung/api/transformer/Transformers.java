package com.cognifide.slung.api.transformer;

import com.google.common.collect.ImmutableMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class Transformers<T> {

  private final Map<Class<?>, Transformer<T>> transformers;

  public Transformers() {
    transformers = ImmutableMap.<Class<?>, Transformer<T>>builder()
        .put(ArrayList.class, new ArrayListTransformer<T>())
        .put(LinkedList.class, new LinkedListTransformer<T>())
        .put(HashSet.class, new HashSetTransformer<T>())
        .put(TreeSet.class, new TreeSetTransformer<T>())
        .put(LinkedHashSet.class, new LinkedHashSetTransformer<T>())
        .put(SortedSet.class, new SortedSetTransformer<T>())
        .put(Set.class, new SetTransformer<T>())
        .build();
  }

  public Collection<T> transform(final Class<?> type, final Collection<T> collection) {
    Collection<T> result = collection;
    if (null != type && transformers.containsKey(type)) {
      result = transformers.get(type).transform(collection);
    }
    return result;
  }
}
