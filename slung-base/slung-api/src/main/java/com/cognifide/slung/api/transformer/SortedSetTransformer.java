package com.cognifide.slung.api.transformer;

import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;

public class SortedSetTransformer<T> implements Transformer<T> {
  
  @Override
  public SortedSet<T> transform(Collection<T> collection) {
    return new TreeSet<>(collection);
  }
  
}
