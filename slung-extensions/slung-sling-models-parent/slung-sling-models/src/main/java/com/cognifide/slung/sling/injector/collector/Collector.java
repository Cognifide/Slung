package com.cognifide.slung.sling.injector.collector;

import com.cognifide.slung.api.condition.ConditionContextBuilder;
import com.cognifide.slung.api.condition.analyzer.ConditionAnalyzer;
import com.cognifide.slung.api.util.Optionals;
import com.cognifide.slung.qualifier.ConditionGroup;
import com.cognifide.slung.sling.context.InjectionContext;
import com.cognifide.slung.sling.injector.function.RequestToResourceFunction;
import static com.google.common.base.Preconditions.checkNotNull;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import java.lang.reflect.AnnotatedElement;
import javax.servlet.ServletRequest;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;

public abstract class Collector {

  protected final InjectionContext injectionContext;

  protected ServletRequest request;

  protected Resource resource;

  protected Object value;

  Collector(InjectionContext injectionContext) {
    this.injectionContext = checkNotNull(injectionContext);
  }

  public Object getValue() {
    return value;
  }

  public boolean accepts() {
    boolean result = false;
    if (hasProperAnnotation()) {
      bindParameters();
      result = meetRequirements();
    }
    return result;
  }

  private boolean hasProperAnnotation() {
    return Iterables.all(injectionContext.getAnnotations(), new AnnotationPredicate(injectionContext.getAnnotatedElement()));
  }

  private void bindParameters() {
    bindRequest();
    bindResourceFromAdaptable();
    bindValueFromAdaptable();
  }

  private void bindRequest() {
    request = findRequest();
  }

  private ServletRequest findRequest() {
    return injectionContext.getAdaptable() instanceof ServletRequest
        ? (ServletRequest) injectionContext.getAdaptable()
        : injectionContext.getRequestCatcher().fetchRequest().orNull();
  }

  private void bindResourceFromAdaptable() {
    if (injectionContext.getAdaptable() instanceof Resource) {
      resource = (Resource) injectionContext.getAdaptable();
    } else if (injectionContext.getAdaptable() instanceof SlingHttpServletRequest) {
      resource = ((SlingHttpServletRequest) injectionContext.getAdaptable()).getResource();
    } else {
      resource = Optionals.flatten(injectionContext.getRequestCatcher().fetchRequest().transform(RequestToResourceFunction.INSTANCE))
          .orNull();
    }
  }

  private void bindValueFromAdaptable() {
    value = injectionContext.getInjector().getValue(injectionContext.getAdaptable(), injectionContext.getProperyName(), injectionContext.getType(), injectionContext.getAnnotatedElement(), injectionContext.getDisposalCallbackRegistry());
  }

  private boolean meetRequirements() {
    return new ConditionAnalyzer().accepts(ConditionContextBuilder.from(request)
        .resource(resource)
        .annotatedElement(injectionContext.getAnnotatedElement())
        .value(value)
        .defaultValue(readDefaultValue())
        .conditionGroups(ImmutableList.copyOf(readConditionGroups(injectionContext.getAnnotatedElement())))
        .build());
  }

  protected abstract ConditionGroup[] readConditionGroups(final AnnotatedElement annotatedElemnt);

  protected abstract Object readDefaultValue();

  public abstract Object compute();

}
