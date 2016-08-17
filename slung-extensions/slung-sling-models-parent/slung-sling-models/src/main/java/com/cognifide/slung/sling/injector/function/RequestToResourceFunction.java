package com.cognifide.slung.sling.injector.function;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;

public class RequestToResourceFunction implements Function<SlingHttpServletRequest, Optional<Resource>> {

  public static final Function<SlingHttpServletRequest, Optional<Resource>> INSTANCE = new RequestToResourceFunction();

  private RequestToResourceFunction() {
  }

  @Override
  public Optional<Resource> apply(SlingHttpServletRequest request) {
    return Optional.fromNullable(request.getResource());
  }

}
