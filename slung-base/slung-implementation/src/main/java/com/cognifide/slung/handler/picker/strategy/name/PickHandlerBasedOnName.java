package com.cognifide.slung.handler.picker.strategy.name;

import com.cognifide.slung.api.context.handler.HandlerContext;
import com.cognifide.slung.api.handler.Handler;
import com.cognifide.slung.api.handler.picker.HandlerPickingStrategy;
import com.cognifide.slung.handler.picker.strategy.Picker;
import com.cognifide.slung.handler.picker.strategy.tracker.FilteredSortedHandlerServiceTracker;
import com.cognifide.slung.handler.picker.strategy.tracker.ImmutableFilteredSortedHandlerServiceTracker;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@Component(
    label = com.cognifide.slung.api.Constants.LABEL_PREFIX + "Pick handler based on name",
    description = "Select handler based on name available in context. Name to select explicitly: " + PickHandlerBasedOnName.SHORT_NAME + ".",
    metatype = true)
@Properties(
    @Property(name = org.osgi.framework.Constants.SERVICE_RANKING, propertyPrivate = false,
        intValue = 100))
public class PickHandlerBasedOnName implements HandlerPickingStrategy {

  private static final Logger LOGGER = LoggerFactory.getLogger(PickHandlerBasedOnName.class);

  public static final String SHORT_NAME = "name";

  private final Picker<Handler> picker = new Picker<Handler>();

  private FilteredSortedHandlerServiceTracker<Handler> tracker;

  @Activate
  protected void activate(final BundleContext bundleContext) {
    tracker = ImmutableFilteredSortedHandlerServiceTracker.from(bundleContext, Handler.class);
  }

  @Override
  public String getName() {
    return SHORT_NAME;
  }

  @Override
  public Optional<Handler> pickUsing(HandlerContext handlerContext) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("Handler selection by name started for {} resource with {} property name",
          handlerContext.getResourcePath(), handlerContext.getPropertyName());
    }

    final Predicate<Handler> predicate = new HandlerNamePredicate(handlerContext);

    return picker.findHandlerUsing(tracker.findServicesFor(handlerContext), predicate);
  }

  @Deactivate
  protected void deactivate() {
    tracker.close();
  }
}
