package com.cognifide.slung.api.handler.picker.predicates;

import com.cognifide.slung.api.handler.Handler;
import com.google.common.base.Predicate;
import com.google.common.collect.Sets;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;

public class UniqueHandlerNamePredicate implements Predicate<Handler> {

  private final Set<String> names;

  public UniqueHandlerNamePredicate() {
    this.names = Sets.newHashSet();
  }

  @Override
  public boolean apply(Handler handler) {
    if (StringUtils.isBlank(handler.getName())) {
      throw new IllegalArgumentException("Found " + handler.getClass() + " with empty name.");
    }
    return names.add(handler.getName());
  }

}
