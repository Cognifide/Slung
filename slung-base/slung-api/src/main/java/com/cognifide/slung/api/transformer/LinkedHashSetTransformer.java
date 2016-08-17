package com.cognifide.slung.api.transformer;

import java.util.Collection;
import java.util.LinkedHashSet;

public class LinkedHashSetTransformer<T> implements Transformer<T> {

  @Override
  public LinkedHashSet<T> transform(Collection<T> collection) {
    return new LinkedHashSet<>(collection);
  }
  
}
