package com.cognifide.slung.api.foster;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;

public class ListMap<T> {

  private final List<String> order;

  private final Map<String, T> data;

  private ListMap(int expectedSize) {
    this.order = Lists.newArrayListWithExpectedSize(expectedSize);
    this.data = Maps.newHashMapWithExpectedSize(expectedSize);
  }

  public void put(final String key, final T value) {
    order.add(key);
    data.put(key, value);
  }

  public void put(final int index, final String key, final T value) {
    order.add(index, key);
    data.put(key, value);
  }

  public int indexOf(final String key) {
    return order.indexOf(key);
  }

  public boolean containsKey(final String key) {
    return data.containsKey(key);
  }

  public List<T> toList() {
    List<T> result = Lists.newArrayListWithCapacity(order.size());
    for (String key : order) {
      result.add(data.get(key));
    }
    return result;
  }

  public static <T> ListMap<T> newListMapWithExpectedSize(int expectedSize) {
    return new ListMap<>(expectedSize);
  }
}
