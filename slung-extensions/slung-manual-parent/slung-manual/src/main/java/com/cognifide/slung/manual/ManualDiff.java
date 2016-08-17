package com.cognifide.slung.manual;

import com.google.common.base.Preconditions;
import java.lang.reflect.Field;
import org.apache.sling.api.SlingHttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ManualDiff {

  private final static Logger LOGGER = LoggerFactory.getLogger(ManualDiff.class);

  private final SlingHttpServletRequest request;

  public ManualDiff(SlingHttpServletRequest request) {
    this.request = request;
  }

  @SuppressWarnings("unchecked")
  public <T> Object diff(Object that, String propertyName) {
    Preconditions.checkNotNull(that);
    Preconditions.checkNotNull(propertyName);
    T t = null;
    try {
      Field field = that.getClass().getDeclaredField(propertyName);
      field.setAccessible(true);
      t = diff(field, propertyName, (T) field.get(that));
    } catch (IllegalAccessException x) {
      log(x, propertyName);
    } catch (IllegalArgumentException x) {
      log(x, propertyName);
    } catch (NoSuchFieldException x) {
      LOGGER.error("Error while reading field from object", x);
    }
    return t;
  }

  private void log(Throwable t, String fieldName) {
    LOGGER.error("Error while reading value from field " + fieldName, t);
  }

  @SuppressWarnings("unchecked")
  public <T> Object diff(Object that, Field field) {
    Preconditions.checkNotNull(that);
    Preconditions.checkNotNull(field);
    T t = null;
    try {
      field.setAccessible(true);
      t = diff(field, field.getName(), (T) field.get(that));
    } catch (IllegalAccessException x) {
      log(x, field.getName());
    } catch (IllegalArgumentException x) {
      log(x, field.getName());
    }
    return t;
  }

  public <T> T diff(Field field, T value) {
    Preconditions.checkNotNull(field);
    return diff(field, field.getName(), value);
  }

  @SuppressWarnings("unchecked")
  public <T> T diff(Field field, String propertyName, T value) {
    Preconditions.checkNotNull(field);
    Preconditions.checkNotNull(propertyName);
    Preconditions.checkNotNull(value);

    T t = value;
    return t;
  }
}
