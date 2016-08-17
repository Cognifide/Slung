package com.cognifide.slung.api.transformer;

import java.util.Collection;
import java.util.TreeSet;

public class TreeSetTransformer<T> implements Transformer<T> {
  
  @Override
  public TreeSet<T> transform(Collection<T> collection) {
    return new TreeSet<>(collection);
  }
  
}
