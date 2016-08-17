package com.cognifide.slung.api.handler.picker.predicates;

import com.cognifide.slung.api.handler.Handler;
import com.google.common.base.Predicate;

public class ActiveHandlerPredicate implements Predicate<Handler> {

    public static final Predicate<Handler> INSTANCE = new ActiveHandlerPredicate();

    @Override
    public boolean apply(Handler handler) {
      return handler.isActive();
    }

  }
