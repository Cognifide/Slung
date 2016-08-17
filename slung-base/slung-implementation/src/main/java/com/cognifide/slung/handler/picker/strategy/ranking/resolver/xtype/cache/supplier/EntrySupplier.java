package com.cognifide.slung.handler.picker.strategy.ranking.resolver.xtype.cache.supplier;

import com.cognifide.slung.handler.picker.strategy.ranking.resolver.xtype.cache.KeysValueChainCache;
import com.cognifide.slung.handler.picker.strategy.ranking.resolver.xtype.cache.LruChainCache;
import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;

public class EntrySupplier implements Supplier<KeysValueChainCache.Entry<String, String, String>> {

  private final Resource resource;

  private final String propertyName;

  private final String key;

  public EntrySupplier(Resource resource, String propertyName, String key) {
    this.resource = resource;
    this.propertyName = propertyName;
    this.key = key;
  }

  @Override
  public KeysValueChainCache.Entry<String, String, String> get() {
    Optional<Resource> widget = new WidgetSeeker(resource, propertyName).seek();
    return new LruChainCache.Entry(readPathFrom(widget), key, readXTypeFrom(widget));
  }

  private String readPathFrom(Optional<Resource> widget) {
    return widget.transform(ResourceToPathFunction.INSTANCE).or(StringUtils.EMPTY);
  }

  private String readXTypeFrom(Optional<Resource> widget) {
    return widget.transform(ResourceToXTypeFunction.INSTANCE).or(StringUtils.EMPTY);
  }

}
