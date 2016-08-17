package com.cognifide.slung.handler.picker.strategy.tracker;

import com.cognifide.slung.api.context.handler.HandlerContext;
import com.cognifide.slung.api.handler.Handler;
import com.cognifide.slung.api.handler.picker.HandlerPickingStrategies;
import com.google.common.base.Preconditions;
import org.apache.sling.commons.osgi.SortingServiceTracker;
import org.osgi.framework.BundleContext;

public class ImmutableFilteredSortedHandlerServiceTracker<T extends Handler> implements FilteredSortedHandlerServiceTracker<T> {

  private final SortingServiceTracker<T> tracker;

  public ImmutableFilteredSortedHandlerServiceTracker(BundleContext bundleContext, Class<T> type) {
    tracker = new SortingServiceTracker<>(bundleContext, type.getName());
  }

  @Override
  public Iterable<T> findServicesFor(final HandlerContext handlerContext) {
    return HandlerPickingStrategies.filter(Preconditions.checkNotNull(handlerContext), tracker.getSortedServices());
  }

  @Override
  public void close() {
    tracker.close();
  }

  void open() {
    tracker.open();
  }

  public static <T extends Handler> FilteredSortedHandlerServiceTracker<T> from(final BundleContext bundleContext, final Class<T> type) {
    ImmutableFilteredSortedHandlerServiceTracker<T> tracker = new ImmutableFilteredSortedHandlerServiceTracker<>(bundleContext, type);
    tracker.open();
    return tracker;
  }
}
