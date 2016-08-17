package com.cognifide.slung.sling.tracker;

import com.cognifide.slung.api.Constants;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.annotation.CheckForNull;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.models.spi.Injector;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

@Service
@Component(
    label = Constants.LABEL_PREFIX + "Sling Injectors Tracker",
    description = "Service tracking injectors from sling models.")
public class SlingInjectorsTracker implements InjectorsTracker {

  private final Lock lock = new ReentrantLock();

  private boolean opened;

  private ServiceTracker serviceTracker;

  @Activate
  protected void activate(final BundleContext context) {
    opened = false;
    serviceTracker = new ServiceTracker(context, Injector.class.getName(), null);
  }

  @Override
  public void startTracking() {
    try {
      lock.lock();
      if (!opened) {
        serviceTracker.open();
        opened = true;
      }
    } finally {
      lock.unlock();
    }
  }

  @CheckForNull
  @Override
  public Injector find(String injectorClass) {
    Injector injector = null;

    ServiceReference[] serviceReferences = serviceTracker.getServiceReferences();
    if (ArrayUtils.isNotEmpty(serviceReferences)) {
      for (ServiceReference serviceReference : serviceReferences) {
        Object service = serviceTracker.getService(serviceReference);
        if (service.getClass().getName().equals(injectorClass)) {
          injector = (Injector) service;
        }
      }
    }

    return injector;
  }

  @Deactivate
  protected void deactivate() {
    serviceTracker.close();
    opened = false;
  }
}
