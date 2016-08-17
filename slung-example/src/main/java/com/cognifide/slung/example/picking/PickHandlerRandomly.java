package com.cognifide.slung.example.picking;

import com.cognifide.slung.api.Constants;
import com.cognifide.slung.api.context.handler.HandlerContext;
import com.cognifide.slung.api.handler.Handler;
import static com.cognifide.slung.api.handler.picker.HandlerPickingStrategies.filter;
import com.cognifide.slung.api.handler.picker.HandlerPickingStrategy;
import com.google.common.base.Optional;
import com.google.common.collect.Iterables;
import java.security.SecureRandom;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.commons.osgi.SortingServiceTracker;
import org.osgi.framework.BundleContext;

@Service
@Component(
    label = Constants.LABEL_PREFIX + "Example - Pick handler randomly",
    description = "Example pick handler randomly demonstraiting auto handler picking strategy discovery.",
    metatype = true)
@Properties(
    @Property(name = org.osgi.framework.Constants.SERVICE_RANKING, propertyPrivate = false,
        intValue = 0))
public class PickHandlerRandomly implements HandlerPickingStrategy {

  private SortingServiceTracker<Handler> serviceTracker;

  @Activate
  protected void activate(final BundleContext bundleContext) {
    serviceTracker = new SortingServiceTracker<Handler>(bundleContext, Handler.class.getName());
    serviceTracker.open();
  }

  @Override
  public String getName() {
    return "random";
  }

  @Override
  public Optional<Handler> pickUsing(HandlerContext handlerContext) {
    Iterable<Handler> handlers = filter(handlerContext, serviceTracker.getSortedServices());
    return Iterables.isEmpty(handlers)
        ? Optional.<Handler>absent()
        : Optional.of(Iterables.get(handlers, new SecureRandom().nextInt(Iterables.size(handlers))));
  }

  @Deactivate
  protected void deactivate() {
    serviceTracker.close();
  }

}
