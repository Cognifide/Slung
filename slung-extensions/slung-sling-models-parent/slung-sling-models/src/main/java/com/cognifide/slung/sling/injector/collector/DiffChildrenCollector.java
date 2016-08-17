package com.cognifide.slung.sling.injector.collector;

import com.cognifide.slung.api.context.handler.seeker.OldResourceSeeker;
import com.cognifide.slung.api.util.Optionals;
import com.cognifide.slung.qualifier.ConditionGroup;
import com.cognifide.slung.qualifier.Diff;
import com.cognifide.slung.sling.context.InjectionContext;
import com.cognifide.slung.sling.injector.collector.children.OldResourceToChildrenFunction;
import com.google.common.base.Optional;
import java.lang.reflect.AnnotatedElement;

public class DiffChildrenCollector extends Collector {

  public DiffChildrenCollector(InjectionContext injectionContext) {
    super(injectionContext);
  }

  @Override
  protected ConditionGroup[] readConditionGroups(final AnnotatedElement annotatedElemnt) {
    return annotatedElemnt.getAnnotation(Diff.class).conditionGroups();
  }

  @Override
  protected Object readDefaultValue() {
    return null;
  }

  @Override
  public Object compute() {
    return Optionals.flatten(OldResourceSeeker.using(request, resource).seek()
        .transform(new OldResourceToChildrenFunction(injectionContext, resource, value)))
        .or(Optional.fromNullable(value)).orNull();
  }

}
