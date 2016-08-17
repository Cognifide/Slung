package com.cognifide.slung.sling.tracker;

import org.apache.sling.models.spi.Injector;

public interface InjectorsTracker {

  void startTracking();
  
  Injector find(String injectorClass);

}
