package com.cognifide.slung.slice.processor.children;

import com.cognifide.slice.api.provider.ModelProvider;
import com.cognifide.slung.api.foster.FosterParent;
import java.util.List;
import org.apache.sling.api.resource.Resource;

class SearchAndMergeChildrenJoiner extends FosterParent<Resource> {

  private final ModelProvider modelProvider;

  private final Class<?> childType;

  SearchAndMergeChildrenJoiner(Iterable<Resource> newChildren, Iterable<Resource> oldChildren, ModelProvider modelProvider, Class<?> childType) {
    super(newChildren, oldChildren);
    this.modelProvider = modelProvider;
    this.childType = childType;
  }

  @Override
  protected Resource transformNew(Resource newChild, int index) {
    return newChild;
  }

  @Override
  protected Resource transformOld(Resource oldChild) {
    return oldChild;
  }

  @Override
  protected List<?> transform(List<Resource> children) {
    return modelProvider.getListFromResources(childType, children.iterator());
  }
}
