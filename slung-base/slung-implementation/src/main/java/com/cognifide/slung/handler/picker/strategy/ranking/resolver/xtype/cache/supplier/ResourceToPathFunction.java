package com.cognifide.slung.handler.picker.strategy.ranking.resolver.xtype.cache.supplier;

import com.google.common.base.Function;
import org.apache.sling.api.resource.Resource;

public class ResourceToPathFunction implements Function<Resource, String> {

  public static final Function<Resource, String> INSTANCE = new ResourceToPathFunction();

  @Override
  public String apply(Resource resource) {
    return resource.getPath();
  }

}
