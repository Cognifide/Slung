package com.cognifide.slung.api.transformer;

import java.util.Collection;

public interface Transformer<T> {

  Collection<T> transform(Collection<T> collection);
}
