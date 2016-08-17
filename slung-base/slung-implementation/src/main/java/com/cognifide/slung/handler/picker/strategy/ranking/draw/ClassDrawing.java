package com.cognifide.slung.handler.picker.strategy.ranking.draw;

import static com.google.common.base.Preconditions.checkNotNull;
import org.apache.commons.lang3.StringUtils;

public class ClassDrawing {

  private final String className;

  public ClassDrawing(Class<?> type) {
    this.className = checkNotNull(type, "Cannot create drawing of null class.").getSimpleName();
  }

  public String create(int width) {
    return StringUtils.center(StringUtils.abbreviate(className, width), width);
  }

}
