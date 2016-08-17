package com.cognifide.slung.handler.picker.strategy.ranking.resolver.xtype.cache;

import com.google.common.base.Supplier;

public interface KeysValueChainCache<K1, K2, V> {

  void removeWithFirstKey(K1 key1);

  V getOrPutWithSecondKey(K2 key2, Supplier<Entry<K1, K2, V>> supplier);

  void clear();

  interface Entry<K1, K2, V> {

    K1 getFirstKey();

    K2 getSecondKey();

    V getValue();
  }
}
