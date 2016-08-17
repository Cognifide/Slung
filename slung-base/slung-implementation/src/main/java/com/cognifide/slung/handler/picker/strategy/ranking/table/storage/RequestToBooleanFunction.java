package com.cognifide.slung.handler.picker.strategy.ranking.table.storage;

import com.google.common.base.Function;
import javax.servlet.ServletRequest;

public class RequestToBooleanFunction implements Function<ServletRequest, Boolean> {

  private final String attributeName;

  public RequestToBooleanFunction(String attributeName) {
    this.attributeName = attributeName;
  }

  @Override
  public Boolean apply(ServletRequest request) {
    return null != request.getAttribute(attributeName);
  }
}
