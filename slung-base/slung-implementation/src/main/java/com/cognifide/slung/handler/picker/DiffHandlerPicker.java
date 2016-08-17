package com.cognifide.slung.handler.picker;

import com.cognifide.slung.api.Constants;
import com.cognifide.slung.api.context.handler.HandlerContext;
import com.cognifide.slung.api.handler.picker.HandlerPicker;
import com.cognifide.slung.api.handler.picker.HandlerPickingStrategies;
import com.cognifide.slung.api.handler.picker.HandlerPickingStrategy;
import com.cognifide.slung.api.osgi.PropertyReader;
import com.cognifide.slung.api.util.Optionals;
import com.google.common.base.Optional;
import static com.google.common.base.Preconditions.checkNotNull;
import com.google.common.collect.Iterables;
import java.util.Map;
import java.util.NavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Modified;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.apache.felix.scr.annotations.ReferencePolicy;
import org.apache.felix.scr.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static com.google.common.base.Preconditions.checkNotNull;

@Service
@Component(
    label = Constants.LABEL_PREFIX + "Diff handler picker",
    description = "Picks handler based on handler picking strategy",
    metatype = true)
@Properties(
    @Property(name = HandlerPickingStrategies.HANDLER_PICKING_STRATEGY_NAME_PROPERTY, label = HandlerPickingStrategies.HANDLER_PICKING_STRATEGY_NAME_LABEL, description = HandlerPickingStrategies.HANDLER_PICKING_STRATEGY_NAME_DESCRIPTION))
public class DiffHandlerPicker implements HandlerPicker {

  private static final Logger LOGGER = LoggerFactory.getLogger(DiffHandlerPicker.class);

  @Reference(referenceInterface = HandlerPickingStrategy.class, cardinality = ReferenceCardinality.OPTIONAL_MULTIPLE, policy = ReferencePolicy.DYNAMIC, updated = "updateHandlerPickingStrategies")
  private final NavigableMap<Integer, HandlerPickingStrategy> handlerPickingStrategies = new ConcurrentSkipListMap<Integer, HandlerPickingStrategy>();

  private Optional<HandlerPickingStrategy> handlerPickingStrategy;

  private String strategyName;

  @Activate
  protected void activate(final Map<String, Object> configuration) {
    modified(configuration);
  }

  @Modified
  protected void modified(final Map<String, Object> configuration) {
    strategyName = PropertyReader.toString(configuration, HandlerPickingStrategies.HANDLER_PICKING_STRATEGY_NAME_PROPERTY);
    selectHandlerPickingStrategy();
  }

  private void selectHandlerPickingStrategy() {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("Configuration changed, searching for handler picking strategy with name: {}.", strategyName);
      if (StringUtils.isBlank(strategyName)) {
        LOGGER.debug("Strategy name was not set. Handler picking strategy with highest service ranking will be used.");
      }
    }

    handlerPickingStrategy = Iterables.tryFind(handlerPickingStrategies.values(), new HandlerPickingStrategyNamePredicate(strategyName)).or(defaultStrategy());

    if (LOGGER.isDebugEnabled()) {
      if (handlerPickingStrategy.isPresent()) {
        LOGGER.debug("Handler picking strategy with name: {} was selected. Class: {}", strategyName, handlerPickingStrategy.get().getClass().getName());
      } else {
        LOGGER.debug("There was no available handler picking strategy.");
      }
    }
  }

  private Optional<HandlerPickingStrategy> defaultStrategy() {
    return handlerPickingStrategies.isEmpty() ? Optional.<HandlerPickingStrategy>absent() : Optional.of(handlerPickingStrategies.lastEntry().getValue());
  }

  protected void bindHandlerPickingStrategies(final HandlerPickingStrategy handlerPickingStrategy, final Map<String, Object> configuration) {
    addHandlerPickingStrategy(handlerPickingStrategy, configuration);
    selectHandlerPickingStrategy();
  }

  private void addHandlerPickingStrategy(final HandlerPickingStrategy handlerPickingStrategy, final Map<String, Object> configuration) {
    handlerPickingStrategies.put(PropertyReader.toInteger(configuration, org.osgi.framework.Constants.SERVICE_RANKING), handlerPickingStrategy);
    validateStrategies();
  }

  private void validateStrategies() {
    if (!containsStrategiesWithUniqueName()) {
      throw new IllegalStateException("There is name conflict amoung handler picikng strategies. At least two strategies have the same name.");
    }
  }

  private boolean containsStrategiesWithUniqueName() {
    return Iterables.all(handlerPickingStrategies.values(), new UniqueHandlerPickingStrategyNamePredicate());
  }

  protected void updateHandlerPickingStrategies(final HandlerPickingStrategy handlerPickingStrategy, final Map<String, Object> configuration) {
    removeHandlerPickingStrategy(handlerPickingStrategy);
    addHandlerPickingStrategy(handlerPickingStrategy, configuration);
    selectHandlerPickingStrategy();
  }

  protected void unbindHandlerPickingStrategies(final HandlerPickingStrategy handlerPickingStrategy) {
    removeHandlerPickingStrategy(handlerPickingStrategy);
    selectHandlerPickingStrategy();
  }

  private void removeHandlerPickingStrategy(final HandlerPickingStrategy handlerPickingStrategy) {
    Integer key = null;
    for (Map.Entry<Integer, HandlerPickingStrategy> entry : handlerPickingStrategies.entrySet()) {
      if (entry.getValue().getClass().equals(handlerPickingStrategy.getClass())) {
        key = entry.getKey();
      }
    }
    if (null == key) {
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("Handler picking strategy {} was not found in collection. Data malformed.", handlerPickingStrategy.getClass().getName());
      }
    } else {
      handlerPickingStrategies.remove(key);
    }
  }

  @Override
  public Object tryToProcessUsing(final HandlerContext handlerContext) {
    checkNotNull(handlerContext);

    return Optionals.flatten(Optionals.flatten(handlerPickingStrategy
        .transform(new HandlerPickingStrategyToHandlerFunction(handlerContext)))
        .transform(new HandlerToValueFunction(handlerContext)))
        .or(Optional.fromNullable(handlerContext.getOriginalValue()))
        .orNull();
  }
}
