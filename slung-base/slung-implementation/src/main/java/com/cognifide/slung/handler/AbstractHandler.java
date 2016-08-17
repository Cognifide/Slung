package com.cognifide.slung.handler;

import com.cognifide.slung.api.handler.AnnotationAware;
import com.cognifide.slung.api.handler.Handler;
import com.cognifide.slung.api.handler.NameAware;
import com.cognifide.slung.api.handler.PropertyNameAware;
import com.cognifide.slung.api.handler.ResourceTypeAware;
import com.cognifide.slung.api.handler.ValueAware;
import com.cognifide.slung.api.handler.XTypeAware;
import com.cognifide.slung.api.osgi.PropertyReader;
import com.cognifide.slung.api.strategy.RankStrategy;
import java.util.Map;

public abstract class AbstractHandler implements Handler, NameAware, AnnotationAware, ResourceTypeAware, PropertyNameAware, XTypeAware, ValueAware {

  private boolean active;

  private RankStrategy nameStrategy;

  private RankStrategy annotationStrategy;

  private RankStrategy resourceTypeStrategy;

  private RankStrategy propertyNameStrategy;

  private RankStrategy xTypeStrategy;

  private RankStrategy valueStrategy;

  @Override
  public boolean isActive() {
    return active;
  }

  @Override
  public RankStrategy getNameStrategy() {
    return nameStrategy;
  }

  @Override
  public RankStrategy getAnnotationStrategy() {
    return annotationStrategy;
  }

  @Override
  public RankStrategy getResourceTypeStrategy() {
    return resourceTypeStrategy;
  }

  @Override
  public RankStrategy getPropertyNameStrategy() {
    return propertyNameStrategy;
  }

  @Override
  public RankStrategy getXTypeStrategy() {
    return xTypeStrategy;
  }

  @Override
  public RankStrategy getValueStrategy() {
    return valueStrategy;
  }

  protected void activate(Map<String, Object> configuration) {
    active = PropertyReader.toBoolean(configuration, Handlers.ACTIVE_PROPERTY);

    nameStrategy = PropertyReader.toRankStrategy(configuration, Handlers.NAME_STRATEGY_PROPERTY, getName());
    annotationStrategy = PropertyReader.toRankStrategy(configuration, Handlers.ANNOTATION_STRATEGY_PROPERTY, Handlers.ANNOTATIONS_PROPERTY);
    resourceTypeStrategy = PropertyReader.toRankStrategy(configuration, Handlers.RESOURCE_TYPE_STRATEGY_PROPERTY, Handlers.RESOURCE_TYPES_PROPERTY);
    propertyNameStrategy = PropertyReader.toRankStrategy(configuration, Handlers.PROPERTY_NAME_STRATEGY_PROPERTY, Handlers.PROPERTY_NAMES_PROPERTY);
    xTypeStrategy = PropertyReader.toRankStrategy(configuration, Handlers.X_TYPE_STRATEGY_PROPERTY, Handlers.X_TYPES_PROPERTY);
    valueStrategy = PropertyReader.toRankStrategy(configuration, Handlers.VALUE_STRATEGY_PROPERTY, Handlers.VALUES_PROPERTY);
  }
}
