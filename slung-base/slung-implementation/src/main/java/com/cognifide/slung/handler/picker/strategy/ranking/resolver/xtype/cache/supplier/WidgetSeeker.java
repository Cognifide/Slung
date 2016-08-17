package com.cognifide.slung.handler.picker.strategy.ranking.resolver.xtype.cache.supplier;

import com.day.cq.wcm.api.components.Component;
import com.day.cq.wcm.api.components.ComponentManager;
import com.google.common.base.Optional;
import com.google.common.collect.Iterables;
import com.google.common.collect.Queues;
import java.util.Deque;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

public class WidgetSeeker {

  private static final String VALUE_PROPERTY_PREFIX = "./";

  private static final String NAME_PROPERTY = "name";

  private final Resource resource;

  private final String propertyName;

  public WidgetSeeker(Resource resource, String propertyName) {
    this.resource = resource;
    this.propertyName = propertyName;
  }

  public Optional<Resource> seek() {
    Resource dialog = findComponentDialog();
    return null == dialog ? Optional.<Resource>absent() : findWidgetIn(Queues.newArrayDeque(dialog.getChildren()));
  }

  private Resource findComponentDialog() {
    Resource result = null;
    final ResourceResolver resourceResolver = retrieveResourceResolver();
    Component component = findComponent(resourceResolver);
    if (null != component) {
      result = resourceResolver.getResource(component.getDialogPath());
    }
    return result;
  }

  private ResourceResolver retrieveResourceResolver() {
    return resource.getResourceResolver();

  }

  private Component findComponent(ResourceResolver resourceResolver) {
    ComponentManager componentManager = resourceResolver.adaptTo(ComponentManager.class);
    return componentManager.getComponentOfResource(resource);
  }

  private Optional<Resource> findWidgetIn(Deque<Resource> widgets) {
    Resource result = null;

    while (!widgets.isEmpty() && null == result) {
      Resource widget = widgets.pop();
      if (widgetNameMatches(widget)) {
        result = widget;
      } else {
        Iterables.addAll(widgets, widget.getChildren());
      }
    }

    return Optional.fromNullable(result);
  }

  private boolean widgetNameMatches(Resource widget) {
    return StringUtils.equals(VALUE_PROPERTY_PREFIX + propertyName, widget.getValueMap().get(NAME_PROPERTY, String.class));
  }
}
