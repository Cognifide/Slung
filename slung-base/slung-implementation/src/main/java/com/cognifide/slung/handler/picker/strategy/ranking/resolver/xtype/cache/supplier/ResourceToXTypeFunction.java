package com.cognifide.slung.handler.picker.strategy.ranking.resolver.xtype.cache.supplier;

import com.google.common.base.Function;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;

public class ResourceToXTypeFunction implements Function<Resource, String> {

  private static final String XTYPE_PROPERTY = "xtype";

  public static final Function<Resource, String> INSTANCE = new ResourceToXTypeFunction();

  @Override
  public String apply(Resource resource) {
    return resource.getValueMap().get(XTYPE_PROPERTY, StringUtils.EMPTY);
  }

}
