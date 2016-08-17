package com.cognifide.slung.sling.injector;

import com.cognifide.slung.api.Constants;
import com.cognifide.slung.api.handler.picker.HandlerPicker;
import com.cognifide.slung.qualifier.Diff;
import com.cognifide.slung.sling.context.InjectionContextBuilder;
import com.cognifide.slung.sling.filter.RequestCatcher;
import com.cognifide.slung.sling.injector.collector.Collector;
import com.cognifide.slung.sling.injector.collector.DiffCollector;
import com.cognifide.slung.sling.tracker.InjectorsTracker;
import com.google.common.collect.ImmutableList;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Type;
import java.util.List;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.models.spi.DisposalCallbackRegistry;
import org.apache.sling.models.spi.Injector;

@Service
@Component(
    label = Constants.LABEL_PREFIX + "Diff Injector",
    description = "Injector which injects diff comparison result.")
@Property(name = org.osgi.framework.Constants.SERVICE_RANKING, intValue = Injectors.DIFF_INJECTOR_RANKING)
public class DiffInjector implements Injector {

  private static final List<Class<? extends Annotation>> ANNOTATIONS = ImmutableList.<Class<? extends Annotation>>of(Diff.class);

  @Reference
  private RequestCatcher requestCatcher;

  @Reference
  private InjectorsTracker injectorsTracker;

  @Reference
  private HandlerPicker handlerPicker;

  @Activate
  protected void activate() {
    injectorsTracker.startTracking();
  }

  @Override
  public String getName() {
    return Injectors.DIFF_INJECTOR_NAME;
  }

  @Override
  public Object getValue(Object adaptable, String propertyName, Type type, AnnotatedElement annotatedElement, DisposalCallbackRegistry disposalCallbackRegistry) {
    Collector collector = new DiffCollector(new InjectionContextBuilder(requestCatcher, findInjector())
        .handlerPicker(handlerPicker)
        .adaptable(adaptable)
        .propertyName(propertyName)
        .type(type)
        .annotations(ANNOTATIONS)
        .annotatedElement(annotatedElement)
        .disposalCallbackRegistry(disposalCallbackRegistry)
        .build());
    return collector.accepts() ? collector.compute() : null;
  }

  private Injector findInjector() {
    return injectorsTracker.find(Injectors.VALUE_MAP_INJECTOR_CLASS);
  }
}
