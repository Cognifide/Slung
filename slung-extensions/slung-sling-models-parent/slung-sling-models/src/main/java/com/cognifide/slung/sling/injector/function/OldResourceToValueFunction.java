package com.cognifide.slung.sling.injector.function;

import com.cognifide.slung.sling.context.InjectionContext;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import org.apache.sling.api.resource.Resource;

public class OldResourceToValueFunction implements Function<Resource, Optional<Object>> {

  private final InjectionContext injectionContext;
  
  public OldResourceToValueFunction(InjectionContext injectionContext) {
    this.injectionContext = injectionContext;
  }

  @Override
  public Optional<Object> apply(Resource oldResource) {
    return Optional.fromNullable(useDefaultIfNull(getValueFrom(oldResource)));
  }

  private Object getValueFrom(Resource oldResource) {
    return oldResource.getValueMap().get(injectionContext.getProperyName());
  }

  private Object useDefaultIfNull(Object value) {
    return null == value ? new DefaultValueSeeker(injectionContext).produce() : value;
  }
}
