package com.cognifide.slung.api.transformer;

import java.util.Collection;
import java.util.LinkedList;

public class LinkedListTransformer<T> implements Transformer<T> {
  
  @Override
  public LinkedList<T> transform(Collection<T> collection) {
    return new LinkedList<>(collection);
  }
  
}
