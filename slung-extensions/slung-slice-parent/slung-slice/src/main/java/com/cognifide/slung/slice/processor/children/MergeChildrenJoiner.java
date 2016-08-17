package com.cognifide.slung.slice.processor.children;

import com.cognifide.slice.api.provider.ModelProvider;
import com.cognifide.slung.api.foster.FosterParent;
import java.lang.reflect.Array;
import java.util.List;
import org.apache.sling.api.resource.Resource;

class MergeChildrenJoiner extends FosterParent<Object> {

  private final ModelProvider modlerProvider;

  private final Class<?> childType;

  private final Object value;

  MergeChildrenJoiner(Iterable<Resource> newChildren, Iterable<Resource> oldChildren, ModelProvider modlerProvider, Class<?> childType, Object value) {
    super(newChildren, oldChildren);
    this.modlerProvider = modlerProvider;
    this.childType = childType;
    this.value = value;
  }

  @Override
  protected Object transformNew(final Resource child, final int index) {
    return value.getClass().isArray() ? Array.get(value, index) : ((List) value).get(index);
  }

  @Override
  protected Object transformOld(final Resource child) {
    return modlerProvider.get(childType, child);
  }

  @Override
  protected List<?> transform(List<Object> children) {
    return children;
  }

}
