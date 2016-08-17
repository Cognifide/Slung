package com.cognifide.slung.sling.injector.collector.children;

import com.cognifide.slung.api.foster.FosterParent;
import java.util.List;
import org.apache.sling.api.resource.Resource;

public class ChildrenResourceJoiner extends FosterParent<Resource> {

  public ChildrenResourceJoiner(Iterable<Resource> newChildren, Iterable<Resource> oldChildren) {
    super(newChildren, oldChildren);
  }

  @Override
  protected Resource transformNew(Resource child, int index) {
    return child;
  }

  @Override
  protected Resource transformOld(Resource child) {
    return child;
  }

  @Override
  protected List<Resource> transform(List<Resource> result) {
    return result;
  }

}
