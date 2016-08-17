package com.cognifide.slung.api.transformer;

import java.util.ArrayList;
import java.util.Collection;

public class ArrayListTransformer<T> implements Transformer<T> {

  @Override
  public ArrayList<T> transform(Collection<T> collection) {
    return new ArrayList<>(collection);
  }
  
}
