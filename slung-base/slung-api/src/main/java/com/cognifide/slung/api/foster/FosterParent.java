package com.cognifide.slung.api.foster;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import org.apache.sling.api.resource.Resource;

public abstract class FosterParent<T> {

  private final Iterable<Resource> newChildren;

  private final List<Resource> oldChildren;

  private List<T> result;

  public FosterParent(Iterable<Resource> newChildren, Iterable<Resource> oldChildren) {
    this.newChildren = newChildren;
    this.oldChildren = Lists.newArrayList(oldChildren);
    this.result = Collections.<T>emptyList();
  }

  public FosterParent<T> adopt() {
    ListMap<T> listMap = populateWithChildren();

    final ListIterator<Resource> iterator = oldChildren.listIterator();
    while (iterator.hasNext()) {
      int index = iterator.nextIndex();
      final Resource child = iterator.next();

      if (!listMap.containsKey(child.getName())) {
        final int position = findPositionToInsertChild(listMap, index);
        if (isValid(position)) {
          listMap.put(position, child.getName(), transformOld(child));
        } else {
          listMap.put(child.getName(), transformOld(child));
        }
      }
    }

    result = listMap.toList();

    return this;
  }

  private ListMap<T> populateWithChildren() {
    ListMap<T> listMap = ListMap.newListMapWithExpectedSize(16);
    int index = 0;
    for (Resource child : newChildren) {
      listMap.put(child.getName(), transformNew(child, index));
      index++;
    }
    return listMap;
  }

  private int findPositionToInsertChild(final ListMap<T> listMap, final int index) {
    int position = findPositionToInsertChildSearchingForChildInFront(index, listMap);
    if (!isValid(position)) {
      position = findPositionToInsertChildSearchingForChildrenAfter(index, listMap);
    }
    return position;
  }

  private int findPositionToInsertChildSearchingForChildInFront(final int index, final ListMap<T> listMap) {
    int position = -1;

    final ListIterator<Resource> iterator = oldChildren.listIterator(index);
    while (iterator.hasPrevious() && !isValid(position)) {
      position = listMap.indexOf(iterator.previous().getName());
    }

    if (isValid(position)) {
      position++;
    }

    return position;
  }

  private boolean isValid(int position) {
    return -1 < position;
  }

  private int findPositionToInsertChildSearchingForChildrenAfter(final int index, final ListMap<T> listMap) {
    int position = -1;

    final ListIterator<Resource> iterator = oldChildren.listIterator(index);
    while (iterator.hasNext() && !isValid(position)) {
      position = listMap.indexOf(iterator.next().getName());
    }

    return position;
  }

  public List<?> thenCallChildren() {
    return transform(result);
  }

  protected abstract T transformNew(final Resource child, final int index);

  protected abstract T transformOld(final Resource child);

  protected abstract List<?> transform(final List<T> result);
}
