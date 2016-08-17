package com.cognifide.slung.api.handler;

import com.cognifide.slung.api.strategy.RankStrategy;

public interface AnnotationAware extends Handler {

  RankStrategy getAnnotationStrategy();
}
