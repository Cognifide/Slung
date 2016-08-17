package com.cognifide.slung.handler.picker.strategy.ranking.resolver.xtype.cache;

import com.google.common.base.Supplier;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import java.util.Deque;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LruChainCache implements KeysValueChainCache<String, String, String> {

  private static final Logger LOGGER = LoggerFactory.getLogger(LruChainCache.class);

  private final ReadWriteLock lock;

  private final Map<String, String> cache;

  private final Deque<String> lru;

  private final int maxSize;

  public LruChainCache(int maxSize) {
    this.lock = new ReentrantReadWriteLock();
    this.cache = Maps.newHashMap();
    this.lru = Queues.newArrayDeque();
    this.maxSize = maxSize;
  }

  @Override
  public void removeWithFirstKey(String key1) {
    try {
      lock.readLock().lock();
      if (cache.containsKey(key1)) {
        removeWithFirstKeyFromCacheAndLru(key1);
      }
    } finally {
      lock.readLock().unlock();
    }
  }

  private void removeWithFirstKeyFromCacheAndLru(String key1) {
    lock.readLock().unlock();
    try {
      lock.writeLock().lock();
      if (cache.containsKey(key1)) {
        String key2 = cache.remove(key1);
        if (null != key2) {
          cache.remove(key2);
          lru.removeFirstOccurrence(key2);
        }
      }
    } finally {
      lock.readLock().lock();
      lock.writeLock().unlock();
    }
  }

  @Override
  public String getOrPutWithSecondKey(String key2, Supplier<KeysValueChainCache.Entry<String, String, String>> supplier) {
    String value = StringUtils.EMPTY;
    try {
      lock.readLock().lock();
      if (cache.containsKey(key2)) {
        value = cache.get(key2);
        if (LOGGER.isDebugEnabled()) {
          LOGGER.debug("Key '{}' was in cache. Received value: '{}'", key2, value);
        }
      } else {
        value = putAndGet(key2, supplier);
        if (LOGGER.isDebugEnabled()) {
          LOGGER.debug("Key '{}' was not in cache. Computed value: '{}'", key2, value);
        }
      }

      refreshUsage(key2);
    } finally {
      lock.readLock().unlock();
    }
    return value;
  }

  private String putAndGet(String key2, Supplier<KeysValueChainCache.Entry<String, String, String>> supplier) {
    lock.readLock().unlock();
    String value = StringUtils.EMPTY;
    KeysValueChainCache.Entry<String, String, String> entry = supplier.get();
    try {
      lock.writeLock().lock();
      value = cache.containsKey(key2) ? cache.get(key2) : put(entry.getFirstKey(), key2, entry.getValue());
    } finally {
      lock.readLock().lock();
      lock.writeLock().unlock();
    }
    return value;
  }

  private String put(String key1, String key2, String value) {
    if (StringUtils.isNotBlank(value)) {
      if (sizeWasExceeded()) {
        clearLeastUsed();
      }

      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("Inserting into cache {} => {} => {}", new Object[]{key1, key2, value});
      }

      cache.put(key1, key2);
      cache.put(key2, value);
    }

    return value;
  }

  private boolean sizeWasExceeded() {
    return lru.size() > maxSize;
  }

  private void clearLeastUsed() {
    String key2 = lru.pollLast();

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("Least used second key '{}' will be removed", key2);
    }

    cache.remove(key2);
    String key1 = findFirstKeyKnowing(key2);
    if (null != key1) {
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("Least used first key '{}' will be removed", key1);
      }

      cache.remove(key1);
    }
  }

  private String findFirstKeyKnowing(String key2) {
    String key1 = null;
    for (Map.Entry<String, String> entry : cache.entrySet()) {
      if (StringUtils.equals(entry.getValue(), key2)) {
        key1 = entry.getKey();
      }
    }
    return key1;
  }

  @Override
  public void clear() {
    cache.clear();
    lru.clear();
  }

  public void refreshUsage(String key2) {
    try {
      lock.readLock().unlock();
      lock.writeLock().lock();
      if (lru.removeFirstOccurrence(key2) && LOGGER.isDebugEnabled()) {
        LOGGER.debug("Key '{}' was already present in cache. Refereshing its position to last to be removed.", key2);
      }
      lru.addFirst(key2);
    } finally {
      lock.readLock().lock();
      lock.writeLock().unlock();
    }
  }

  public static class Entry implements KeysValueChainCache.Entry<String, String, String> {

    private final String key1;

    private final String key2;

    private final String value;

    public Entry(String key1, String key2, String value) {
      this.key1 = key1;
      this.key2 = key2;
      this.value = value;
    }

    @Override
    public String getFirstKey() {
      return key1;
    }

    @Override
    public String getSecondKey() {
      return key2;
    }

    @Override
    public String getValue() {
      return value;
    }

  }
}
