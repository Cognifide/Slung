package com.cognifide.slung.api.osgi;

import com.cognifide.slung.api.strategy.RankStrategies;
import com.cognifide.slung.api.strategy.RankStrategy;
import com.google.common.collect.Sets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;

public final class PropertyReader {

  private static final Map<Class<?>, Object> DEFAULTS = new HashMap<>(4);

  static {
    DEFAULTS.put(Boolean.class, Boolean.FALSE);
    DEFAULTS.put(String.class, StringUtils.EMPTY);
    DEFAULTS.put(Integer.class, 0);
  }

  private PropertyReader() {
    throw new AssertionError();
  }

  public static boolean toBoolean(Map<String, Object> configuration, String propertyName) {
    return toType(configuration, propertyName, Boolean.class);
  }

  private static <T> T toType(Map<String, Object> configuration, String propertyName, Class<T> type) {
    validate(configuration, propertyName);
    final Object o = configuration.get(propertyName);
    return null != o && ClassUtils.isAssignable(type, o.getClass(), true) ? type.cast(o) : defaultFor(type);
  }

  @SuppressWarnings("unchecked")
  private static <T> T defaultFor(Class<T> type) {
    return (T) DEFAULTS.get(type);
  }

  private static void validate(Map<String, Object> configuration, String... propertyName) {
    Objects.requireNonNull(configuration, "Configuration cannot be null.");
    Objects.requireNonNull(propertyName, "Property name cannot be null.");
  }

  public static int toInteger(Map<String, Object> configuration, String propertyName) {
    return toType(configuration, propertyName, Integer.class);
  }

  public static RankStrategy toRankStrategy(Map<String, Object> configuration, String strategyPropertyName, String valuesPropertyName) {
    validate(configuration, strategyPropertyName, valuesPropertyName);
    return RankStrategies.from(toString(configuration, strategyPropertyName))
        .get(toObjects(configuration, valuesPropertyName));
  }

  private static void validate(Map<String, Object> configuration, String strategyPropertyName, String valuesPropertyName) {
    Objects.requireNonNull(configuration, "Configuration cannot be null.");
    Objects.requireNonNull(strategyPropertyName, "Strategy property name cannot be null.");
    Objects.requireNonNull(valuesPropertyName, "Values property name cannot be null.");
  }

  public static String toString(Map<String, Object> configuration, String propertyName) {
    return toType(configuration, propertyName, String.class);
  }

  private static Iterable<Object> toObjects(Map<String, Object> configuration, String propertyName) {
    validate(configuration, propertyName);
    Set<Object> result = Collections.emptySet();
    Object o = configuration.get(propertyName);
    if (null != o) {
      result = o.getClass().isArray() ? Sets.newHashSet((Object[]) o) : Sets.newHashSet(o);
    }
    return result;
  }

  public static Iterable<String> toStrings(Map<String, Object> configuration, String propertyName) {
    validate(configuration, propertyName);
    Set<String> result = Collections.emptySet();
    Object o = configuration.get(propertyName);
    if (null != o) {
      if (o instanceof String) {
        result = Sets.newHashSet((String) o);
      } else if (o instanceof String[]) {
        result = Sets.newHashSet((String[]) o);
      }
    }
    return result;
  }

}
