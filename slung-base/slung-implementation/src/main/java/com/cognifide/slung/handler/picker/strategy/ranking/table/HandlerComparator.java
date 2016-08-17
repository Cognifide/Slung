package com.cognifide.slung.handler.picker.strategy.ranking.table;

import com.cognifide.slung.api.handler.Handler;
import java.util.Comparator;

class HandlerComparator implements Comparator<Handler> {

  public static final Comparator<Handler> INSTANCE = new HandlerComparator();
  
  private HandlerComparator() {
  }
  
  @Override
  public int compare(Handler o1, Handler o2) {
    return o1.getName().compareTo(o2.getName());
  }

}
