package com.cognifide.slung.api.transformer;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class SetTransformer<T> implements Transformer<T> {
  
  @Override
  public Set<T> transform(Collection<T> collection) {
    return new HashSet<>(collection);
  }
  
}
