package com.cognifide.slung.handler;

import com.cognifide.slung.api.Constants;
import com.cognifide.slung.handler.picker.strategy.ranking.PickHandlerBasedOnRanking;

public class Handlers {

  private static final String HANDLERS_PREFIX = "handlers.";

  private static final String STRATEGY_PREFIX = "strategy.";

  private static final String USED_BY = "Usedy by " + PickHandlerBasedOnRanking.SHORT_NAME + " handler picking strategy.";

  public static final String ACTIVE_LABEL = "Active";

  public static final String ACTIVE_DESCRIPTION = "Turns handler off/on";

  public static final String ACTIVE_PROPERTY = Constants.PROPERTY_PREFIX + HANDLERS_PREFIX + "active";

  public static final String NAME_STRATEGY_LABEL = "Name strategy";

  public static final String NAME_STRATEGY_DESCRIPTION = "Strategy used to compare handler name with given handler name. " + USED_BY;

  public static final String NAME_STRATEGY_PROPERTY = Constants.PROPERTY_PREFIX + HANDLERS_PREFIX + STRATEGY_PREFIX + "name";

  public static final String ANNOTATIONS_LABEL = "Accepted annotations";

  public static final String ANNOTATIONS_DESCRIPTION = "Handled annotations. " + USED_BY;

  public static final String ANNOTATIONS_PROPERTY = Constants.PROPERTY_PREFIX + HANDLERS_PREFIX + "annotations";

  public static final String ANNOTATION_STRATEGY_LABEL = "Annotation strategy";

  public static final String ANNOTATION_STRATEGY_DESCRIPTION = "Strategy used to check if field is annotated with given annotations. " + USED_BY;

  public static final String ANNOTATION_STRATEGY_PROPERTY = Constants.PROPERTY_PREFIX + HANDLERS_PREFIX + STRATEGY_PREFIX + "annotation";

  public static final String RESOURCE_TYPES_LABEL = "Accepted resource types";

  public static final String RESOURCE_TYPES_DESCRIPTION = "Handled resource types. " + USED_BY;

  public static final String RESOURCE_TYPES_PROPERTY = Constants.PROPERTY_PREFIX + HANDLERS_PREFIX + "types.resource";

  public static final String RESOURCE_TYPE_STRATEGY_LABEL = "Resource types strategy";

  public static final String RESOURCE_TYPE_STRATEGY_DESCRIPTION = "Strategy used to compare handled resource types with given resource's reousrce types. " + USED_BY;

  public static final String RESOURCE_TYPE_STRATEGY_PROPERTY = Constants.PROPERTY_PREFIX + HANDLERS_PREFIX + STRATEGY_PREFIX + "types.resource";

  public static final String PROPERTY_NAMES_LABEL = "Accepted property names";

  public static final String PROPERTY_NAMES_DESCRIPTION = "Handler property names. " + USED_BY;

  public static final String PROPERTY_NAMES_PROPERTY = Constants.PROPERTY_PREFIX + HANDLERS_PREFIX + "property";

  public static final String PROPERTY_NAME_STRATEGY_LABEL = "Property names strategy";

  public static final String PROPERTY_NAME_STRATEGY_DESCRIPTION = "Strategy used to compare handler property names with given property name. " + USED_BY;

  public static final String PROPERTY_NAME_STRATEGY_PROPERTY = Constants.PROPERTY_PREFIX + HANDLERS_PREFIX + STRATEGY_PREFIX + "property";

  public static final String X_TYPES_LABEL = "Accepted x types";

  public static final String X_TYPES_DESCRIPTION = "Handled x types. " + USED_BY;

  public static final String X_TYPES_PROPERTY = Constants.PROPERTY_PREFIX + HANDLERS_PREFIX + "types.x";

  public static final String X_TYPE_STRATEGY_LABEL = "X types strategy";

  public static final String X_TYPE_STRATEGY_DESCRIPTION = "Strategy used to compare handled x types with given property x type. (Resource dialog will be read.) " + USED_BY;

  public static final String X_TYPE_STRATEGY_PROPERTY = Constants.PROPERTY_PREFIX + HANDLERS_PREFIX + STRATEGY_PREFIX + "types.x";

  public static final String VALUES_LABEL = "Accepted values";

  public static final String VALUES_DESCRIPTION = "Handled values. " + USED_BY;

  public static final String VALUES_PROPERTY = Constants.PROPERTY_PREFIX + HANDLERS_PREFIX + "values";

  public static final String VALUE_STRATEGY_LABEL = "Values strategy";

  public static final String VALUE_STRATEGY_DESCRIPTION = "Strategy used to compare handled values with given property value. " + USED_BY;

  public static final String VALUE_STRATEGY_PROPERTY = Constants.PROPERTY_PREFIX + HANDLERS_PREFIX + STRATEGY_PREFIX + "values";

  private Handlers() {
    throw new AssertionError();
  }
}
