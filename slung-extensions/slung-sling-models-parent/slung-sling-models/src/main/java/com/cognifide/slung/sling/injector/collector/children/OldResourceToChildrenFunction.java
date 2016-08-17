package com.cognifide.slung.sling.injector.collector.children;

import com.cognifide.slung.api.foster.FosterParent;
import com.cognifide.slung.api.foster.AdoptChildrenFunction;
import com.cognifide.slung.sling.context.InjectionContext;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import org.apache.sling.api.resource.Resource;

public class OldResourceToChildrenFunction extends AdoptChildrenFunction {

  private final InjectionContext injectionContext;

  public OldResourceToChildrenFunction(InjectionContext injectionContext, Resource resource, Object value) {
    super(resource, value, injectionContext.getProperyName());
    this.injectionContext = injectionContext;
  }

  @Override
  protected FosterParent<?> join(Iterable<Resource> newChildren, Iterable<Resource> oldChildren) {
    return new ChildrenResourceJoiner(newChildren, oldChildren);
  }

  @Override
  protected Class<?> findContainerClass() {
    final Type type = injectionContext.getType();
    Class<?> result = null;
    if (type instanceof Class) {
      result = (Class<?>) type;
    } else if (type instanceof ParameterizedType) {
      result = (Class<?>) ((ParameterizedType) type).getRawType();
    }
    return result;
  }

}
