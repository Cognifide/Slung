package com.cognifide.slung.api.foster;

import com.cognifide.slung.api.transformer.Transformers;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.Iterables;
import java.util.Collections;
import java.util.List;
import org.apache.sling.api.resource.Resource;

public abstract class AdoptChildrenFunction implements Function<Resource, Optional<Object>> {

  private final Resource resource;

  private final Object value;

  private final String propertyName;

  public AdoptChildrenFunction(Resource resource, Object value, String propertyName) {
    this.resource = resource;
    this.value = value;
    this.propertyName = propertyName;
  }

  @Override
  public Optional<Object> apply(Resource oldResource) {
    return Optional.fromNullable(merge(oldResource));
  }

  private Object merge(final Resource oldResource) {
    final Iterable<Resource> oldChildren = readChildren(oldResource);
    return noChildren(oldChildren) ? value : mergeChildren(oldChildren);
  }

  private Iterable<Resource> readChildren(final Resource resource) {
    final Resource child = resource.getChild(propertyName);
    return null == child ? Collections.<Resource>emptyList() : child.getChildren();
  }

  private boolean noChildren(Iterable<Resource> children) {
    return Iterables.isEmpty(children);
  }

  private Object mergeChildren(final Iterable<Resource> oldChildren) {
    final Iterable<Resource> newChildren = readChildren(resource);
    return transform(join(newChildren, oldChildren).adopt().thenCallChildren());
  }

  protected abstract FosterParent<?> join(final Iterable<Resource> newChildren, final Iterable<Resource> oldChildren);

  private Object transform(final List<?> list) {
    final Class<?> type = findContainerClass();
    return type.isArray() ? list.toArray() : transform(type, list);
  }

  protected abstract Class<?> findContainerClass();

  private <T> Object transform(Class<?> type, List<T> list) {
    return new Transformers<T>().transform(type, list);
  }

}
