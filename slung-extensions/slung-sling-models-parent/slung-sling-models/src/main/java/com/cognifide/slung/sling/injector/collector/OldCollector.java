package com.cognifide.slung.sling.injector.collector;

import com.cognifide.slung.api.context.handler.ValueProducer;
import com.cognifide.slung.api.context.handler.seeker.OldResourceSeeker;
import com.cognifide.slung.api.util.Optionals;
import com.cognifide.slung.qualifier.ConditionGroup;
import com.cognifide.slung.qualifier.Old;
import com.cognifide.slung.sling.context.InjectionContext;
import com.cognifide.slung.sling.injector.function.DefaultValueSeeker;
import com.cognifide.slung.sling.injector.function.OldResourceToValueFunction;
import java.lang.reflect.AnnotatedElement;

public class OldCollector extends Collector {

  private final ValueProducer valueProducer;

  public OldCollector(InjectionContext injectionContext) {
    super(injectionContext);
    this.valueProducer = new DefaultValueSeeker(injectionContext);
  }

  @Override
  protected ConditionGroup[] readConditionGroups(final AnnotatedElement annotatedElemnt) {
    return annotatedElemnt.getAnnotation(Old.class).conditionGroups();
  }

  @Override
  protected Object readDefaultValue() {
    return valueProducer.produce();
  }

  @Override
  public Object compute() {
    return Optionals.flatten(OldResourceSeeker.using(request, resource).seek().transform(new OldResourceToValueFunction(injectionContext)))
        .orNull();
  }

}
