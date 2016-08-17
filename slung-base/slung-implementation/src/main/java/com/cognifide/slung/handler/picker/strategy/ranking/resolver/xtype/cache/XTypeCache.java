package com.cognifide.slung.handler.picker.strategy.ranking.resolver.xtype.cache;

import com.cognifide.slung.api.Constants;
import com.cognifide.slung.handler.picker.strategy.ranking.resolver.xtype.cache.supplier.EntrySupplier;
import com.cognifide.slung.api.osgi.PropertyReader;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Modified;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.jackrabbit.core.fs.FileSystem;
import org.apache.sling.api.SlingConstants;
import org.apache.sling.api.resource.Resource;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

@Service(value = {TypeCache.class, EventHandler.class})
@Component(
    label = Constants.LABEL_PREFIX + "X type cache",
    description = "Cache containing component field - x type mapping.",
    metatype = true)
@Properties({
  @Property(
      name = EventConstants.EVENT_TOPIC,
      value = {
        SlingConstants.TOPIC_RESOURCE_CHANGED,
        SlingConstants.TOPIC_RESOURCE_REMOVED},
      propertyPrivate = true),
  @Property(
      name = EventConstants.EVENT_FILTER,
      value = "(path=/apps/*)",
      propertyPrivate = true)
})
public class XTypeCache implements TypeCache, EventHandler {

  private static final int MAXIMUM_CACHE_SIZE_DEFAULT = 1000;

  @Property(
      label = Constants.LABEL_PREFIX + "X type maximum cache size",
      description = "Enables to define maximum cache size for x type paths. Default is " + MAXIMUM_CACHE_SIZE_DEFAULT,
      intValue = MAXIMUM_CACHE_SIZE_DEFAULT)
  private static final String MAXIMUM_CACHE_SIZE = Constants.PROPERTY_PREFIX + "xtype.cache.size.max";

  private KeysValueChainCache<String, String, String> cache;

  @Activate
  @Modified
  protected void activate(final Map<String, Object> configuration) {
    cache = new LruChainCache(PropertyReader.toInteger(configuration, MAXIMUM_CACHE_SIZE));
  }

  @Deactivate
  protected void deactivate() {
    cache.clear();
  }

  @Override
  public String getOrPut(Resource resource, String propertyName) {
    String result = StringUtils.EMPTY;

    if (isValid(resource, propertyName)) {
      String key = createKey(resource, propertyName);
      result = cache.getOrPutWithSecondKey(key, new EntrySupplier(resource, propertyName, key));
    }

    return result;
  }

  private boolean isValid(Resource resource, String propertyName) {
    return null != resource && StringUtils.isNotBlank(propertyName);
  }

  private String createKey(Resource resource, String propertyName) {
    return resource.getResourceType() + FileSystem.SEPARATOR + propertyName;
  }

  @Override
  public void handleEvent(Event event) {
    cache.removeWithFirstKey(readPathFrom(event));
  }

  private String readPathFrom(Event event) {
    return (String) event.getProperty(SlingConstants.PROPERTY_PATH);
  }
}
