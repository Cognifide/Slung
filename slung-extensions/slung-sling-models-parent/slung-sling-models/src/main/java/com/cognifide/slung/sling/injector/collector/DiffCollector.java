package com.cognifide.slung.sling.injector.collector;

import com.cognifide.slung.api.context.handler.HandlerContextBuilder;
import com.cognifide.slung.api.context.handler.ValueProducer;
import com.cognifide.slung.qualifier.ConditionGroup;
import com.cognifide.slung.qualifier.Diff;
import com.cognifide.slung.sling.context.InjectionContext;
import com.cognifide.slung.sling.injector.function.DefaultValueSeeker;
import java.lang.reflect.AnnotatedElement;
import org.apache.commons.lang3.StringUtils;

public class DiffCollector extends Collector {

  private final ValueProducer valueProducer;

  public DiffCollector(InjectionContext injectionContext) {
    super(injectionContext);
    this.valueProducer = new DefaultValueSeeker(injectionContext);
  }

  @Override
  protected ConditionGroup[] readConditionGroups(final AnnotatedElement annotatedElemnt) {
    return annotatedElemnt.getAnnotation(Diff.class).conditionGroups();
  }

  @Override
  protected Object readDefaultValue() {
    return valueProducer.produce();
  }

  @Override
  public Object compute() {
    return injectionContext.getHandlerPicker().tryToProcessUsing(HandlerContextBuilder.from(request)
        .resource(resource)
        .propertyName(searchForPropertyName())
        .annotatedElement(injectionContext.getAnnotatedElement())
        .handlerName(searchForHandlerName())
        .valueType(injectionContext.getType())
        .value(value)
        .valueProducer(valueProducer)
        .build());
  }

  private String searchForHandlerName() {
    return injectionContext.getAnnotatedElement().getAnnotation(Diff.class).handlerName();
  }

  private String searchForPropertyName() {
    final AnnotatedElement annotatedElement = injectionContext.getAnnotatedElement();
    return StringUtils.defaultIfBlank(annotatedElement.getAnnotation(Diff.class).propertyName(), injectionContext.getProperyName());
  }

}
