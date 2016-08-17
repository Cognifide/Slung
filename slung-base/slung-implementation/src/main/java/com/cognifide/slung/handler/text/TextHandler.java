package com.cognifide.slung.handler.text;

import com.cognifide.slung.api.Constants;
import com.cognifide.slung.api.configuration.CssClassesConfiguration;
import com.cognifide.slung.api.strategy.RankStrategies;
import com.cognifide.slung.handler.Handlers;
import com.cognifide.slung.handler.text.configuration.TextCssClassesConfiguration;
import com.cognifide.slung.handler.text.filter.StringEscapeService;
import com.day.cq.commons.DiffService;
import java.util.Map;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Modified;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.PropertyOption;
import org.apache.felix.scr.annotations.PropertyUnbounded;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;

@Service
@Component(
    label = Constants.LABEL_PREFIX + "Text handler",
    description = "Creates diff for text components. Use \"" + TextHandlers.TEXT_HANDLER_NAME + "\" as handler name in annotations.",
    metatype = true)
@Properties({
  @Property(name = Handlers.ACTIVE_PROPERTY, label = Handlers.ACTIVE_LABEL, description = Handlers.ACTIVE_DESCRIPTION,
      boolValue = true),
  @Property(name = org.osgi.framework.Constants.SERVICE_RANKING, propertyPrivate = false,
      intValue = TextHandlers.TEXT_HANDLER_RANKING),
  @Property(name = Handlers.NAME_STRATEGY_PROPERTY, label = Handlers.NAME_STRATEGY_LABEL, description = Handlers.NAME_STRATEGY_DESCRIPTION,
      options = {
        @PropertyOption(name = RankStrategies.ANY_NAME, value = RankStrategies.ANY_NAME),
        @PropertyOption(name = RankStrategies.CONTAINS_NAME, value = RankStrategies.CONTAINS_NAME),
        @PropertyOption(name = RankStrategies.EQUALS_NAME, value = RankStrategies.EQUALS_NAME),
        @PropertyOption(name = RankStrategies.SKIP_NAME, value = RankStrategies.SKIP_NAME)},
      value = RankStrategies.EQUALS_NAME),
  @Property(name = Handlers.ANNOTATIONS_PROPERTY, label = Handlers.ANNOTATIONS_LABEL, description = Handlers.ANNOTATIONS_DESCRIPTION, unbounded = PropertyUnbounded.ARRAY),
  @Property(name = Handlers.ANNOTATION_STRATEGY_PROPERTY, label = Handlers.ANNOTATION_STRATEGY_LABEL, description = Handlers.ANNOTATION_STRATEGY_DESCRIPTION,
      options = {
        @PropertyOption(name = RankStrategies.ANY_NAME, value = RankStrategies.ANY_NAME),
        @PropertyOption(name = RankStrategies.CONTAINS_NAME, value = RankStrategies.CONTAINS_NAME),
        @PropertyOption(name = RankStrategies.EQUALS_NAME, value = RankStrategies.EQUALS_NAME),
        @PropertyOption(name = RankStrategies.SKIP_NAME, value = RankStrategies.SKIP_NAME)},
      value = RankStrategies.CONTAINS_NAME),
  @Property(name = Handlers.RESOURCE_TYPES_PROPERTY, label = Handlers.RESOURCE_TYPES_LABEL, description = Handlers.RESOURCE_TYPES_DESCRIPTION, unbounded = PropertyUnbounded.ARRAY),
  @Property(name = Handlers.RESOURCE_TYPE_STRATEGY_PROPERTY, label = Handlers.RESOURCE_TYPE_STRATEGY_LABEL, description = Handlers.RESOURCE_TYPE_STRATEGY_DESCRIPTION,
      options = {
        @PropertyOption(name = RankStrategies.ANY_NAME, value = RankStrategies.ANY_NAME),
        @PropertyOption(name = RankStrategies.CONTAINS_NAME, value = RankStrategies.CONTAINS_NAME),
        @PropertyOption(name = RankStrategies.EQUALS_NAME, value = RankStrategies.EQUALS_NAME),
        @PropertyOption(name = RankStrategies.SKIP_NAME, value = RankStrategies.SKIP_NAME)},
      value = RankStrategies.ANY_NAME),
  @Property(name = Handlers.PROPERTY_NAMES_PROPERTY, label = Handlers.PROPERTY_NAMES_LABEL, description = Handlers.PROPERTY_NAMES_DESCRIPTION, unbounded = PropertyUnbounded.ARRAY),
  @Property(name = Handlers.PROPERTY_NAME_STRATEGY_PROPERTY, label = Handlers.PROPERTY_NAME_STRATEGY_LABEL, description = Handlers.PROPERTY_NAME_STRATEGY_DESCRIPTION,
      options = {
        @PropertyOption(name = RankStrategies.ANY_NAME, value = RankStrategies.ANY_NAME),
        @PropertyOption(name = RankStrategies.CONTAINS_NAME, value = RankStrategies.CONTAINS_NAME),
        @PropertyOption(name = RankStrategies.EQUALS_NAME, value = RankStrategies.EQUALS_NAME),
        @PropertyOption(name = RankStrategies.SKIP_NAME, value = RankStrategies.SKIP_NAME)},
      value = RankStrategies.CONTAINS_NAME),
  @Property(name = Handlers.X_TYPES_PROPERTY, label = Handlers.X_TYPES_LABEL, description = Handlers.X_TYPES_DESCRIPTION, unbounded = PropertyUnbounded.ARRAY),
  @Property(name = Handlers.X_TYPE_STRATEGY_PROPERTY, label = Handlers.X_TYPE_STRATEGY_LABEL, description = Handlers.X_TYPE_STRATEGY_DESCRIPTION,
      options = {
        @PropertyOption(name = RankStrategies.ANY_NAME, value = RankStrategies.ANY_NAME),
        @PropertyOption(name = RankStrategies.CONTAINS_NAME, value = RankStrategies.CONTAINS_NAME),
        @PropertyOption(name = RankStrategies.EQUALS_NAME, value = RankStrategies.EQUALS_NAME),
        @PropertyOption(name = RankStrategies.SKIP_NAME, value = RankStrategies.SKIP_NAME)},
      value = RankStrategies.ANY_NAME),
  @Property(name = Handlers.VALUES_PROPERTY, label = Handlers.VALUES_LABEL, description = Handlers.VALUES_DESCRIPTION, unbounded = PropertyUnbounded.ARRAY),
  @Property(name = Handlers.VALUE_STRATEGY_PROPERTY, label = Handlers.VALUE_STRATEGY_LABEL, description = Handlers.VALUE_STRATEGY_DESCRIPTION,
      options = {
        @PropertyOption(name = RankStrategies.ANY_NAME, value = RankStrategies.ANY_NAME),
        @PropertyOption(name = RankStrategies.CONTAINS_NAME, value = RankStrategies.CONTAINS_NAME),
        @PropertyOption(name = RankStrategies.EQUALS_NAME, value = RankStrategies.EQUALS_NAME),
        @PropertyOption(name = RankStrategies.SKIP_NAME, value = RankStrategies.SKIP_NAME)},
      value = RankStrategies.SKIP_NAME)})
public class TextHandler extends AbstractTextHandler {

  @Reference
  private StringEscapeService stringEscapeService;

  @Reference
  private DiffService diffService;

  @Reference
  private TextCssClassesConfiguration configuration;

  @Activate
  @Modified
  @Override
  protected void activate(final Map<String, Object> configuration) {
    super.activate(configuration);
  }

  @Override
  public String getName() {
    return TextHandlers.TEXT_HANDLER_NAME;
  }

  @Override
  protected CssClassesConfiguration findCssClassesConfiguration() {
    return configuration;
  }

  @Override
  protected DiffService findDiffService() {
    return diffService;
  }

  @Override
  protected StringEscapeService findStringEscapeService() {
    return stringEscapeService;
  }

}
