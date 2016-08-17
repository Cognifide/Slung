package com.cognifide.slung.handler.picker.strategy.ranking.resolver.xtype.cache;

import org.apache.sling.api.resource.Resource;

public interface TypeCache {

  String getOrPut(Resource resource, String propertyName);
}
