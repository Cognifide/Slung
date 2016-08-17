package com.cognifide.slung.component.filter.configuration;

import com.cognifide.slung.api.Constants;
import com.cognifide.slung.api.osgi.PropertyReader;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import java.util.Map;
import java.util.Set;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Modified;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.PropertyUnbounded;
import org.apache.felix.scr.annotations.Service;

@Service
@Component(
    label = Constants.LABEL_PREFIX + "Diff component filter configuration",
    description = "Enables previous component state comparison.",
    metatype = true,
    immediate = true)
@Properties({
  @Property(
      name = DiffComponentFilterConfiguration.ENABLE_DIFF_COMPONENT_FILTER_PROPERTY_NAME,
      label = "Enable",
      description = "Enables diff component filter.",
      boolValue = DiffComponentFilterConfiguration.ENABLE_DIFF_COMPONENT_FILTER_PROPERTY_DEFAULT_VALUE),
  @Property(
      name = DiffComponentFilterConfiguration.RESOURCE_TYPE_PROPERTY,
      label = "Resource types",
      description = "Resource types that will be compared to their previous versions.",
      unbounded = PropertyUnbounded.ARRAY)})
public class DiffComponentFilterConfiguration implements ComponentFilterConfiguration {

  static final String ENABLE_DIFF_COMPONENT_FILTER_PROPERTY_NAME = Constants.PROPERTY_PREFIX + "filter.configuration.enabled";

  static final boolean ENABLE_DIFF_COMPONENT_FILTER_PROPERTY_DEFAULT_VALUE = false;

  static final String RESOURCE_TYPE_PROPERTY = Constants.PROPERTY_PREFIX + "filter.configuration.resource.type";

  private boolean enabled;
  
  private final Set<String> resourceTypes = Sets.newConcurrentHashSet();

  @Activate
  protected void activate(final Map<String, Object> configuration) {
    enabled = PropertyReader.toBoolean(configuration, ENABLE_DIFF_COMPONENT_FILTER_PROPERTY_NAME);
    Iterables.addAll(resourceTypes, PropertyReader.toStrings(configuration, RESOURCE_TYPE_PROPERTY));
  }

  @Modified
  protected void modified(final Map<String, Object> configuration) {
    resourceTypes.clear();
    activate(configuration);
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }

  @Override
  public boolean canFilter(String resourceType) {
    return resourceTypes.contains(resourceType);
  }

}
