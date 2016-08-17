package com.cognifide.slung.api.transformer;

import java.util.Collection;
import java.util.HashSet;

public class HashSetTransformer<T> implements Transformer<T> {

  @Override
  public HashSet<T> transform(Collection<T> collection) {
    return new HashSet<>(collection);
  }
  
}
