package com.cognifide.slung.sling.injector;

import com.cognifide.slung.api.Constants;
import com.cognifide.slung.qualifier.Old;
import com.cognifide.slung.sling.context.InjectionContextBuilder;
import com.cognifide.slung.sling.filter.RequestCatcher;
import com.cognifide.slung.sling.injector.collector.Collector;
import com.cognifide.slung.sling.injector.collector.OldCollector;
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
    label = Constants.LABEL_PREFIX + "Old Value Injector",
    description = "Injects old value from information provided in current request.")
@Property(name = org.osgi.framework.Constants.SERVICE_RANKING, intValue = Injectors.OLD_VALUE_INJECTOR_RANKING)
public class OldInjector implements Injector {

  private static final List<Class<? extends Annotation>> ANNOTATIONS = ImmutableList.<Class<? extends Annotation>>of(Old.class);

  @Reference
  private RequestCatcher requestCatcher;

  @Reference
  private InjectorsTracker injectorsTracker;

  @Activate
  protected void activate() {
    injectorsTracker.startTracking();
  }

  @Override
  public String getName() {
    return Injectors.OLD_VALUE_INJECTOR_NAME;
  }

  @Override
  public Object getValue(Object adaptable, String propertyName, Type type, AnnotatedElement annotatedElement, DisposalCallbackRegistry disposalCallbackRegistry) {
    Collector collector = new OldCollector(new InjectionContextBuilder(requestCatcher, findInjector())
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
