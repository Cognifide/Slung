package com.cognifide.slung.sling.context;

import com.cognifide.slung.api.handler.picker.HandlerPicker;
import com.cognifide.slung.sling.filter.RequestCatcher;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Type;
import java.util.List;
import org.apache.sling.models.spi.DisposalCallbackRegistry;
import org.apache.sling.models.spi.Injector;

public class InjectionContextBuilder {

  private final RequestCatcher requestCatcher;

  private final Injector injector;

  private HandlerPicker handlerPicker;

  private Object adaptable;

  private String propertyName;

  private Type type;

  private List<Class<? extends Annotation>> annotations;

  private AnnotatedElement annotatedElement;

  private DisposalCallbackRegistry disposalCallbackRegistry;

  public InjectionContextBuilder(RequestCatcher requestCatcher, Injector injector) {
    this.requestCatcher = requestCatcher;
    this.injector = injector;
  }

  public InjectionContextBuilder handlerPicker(HandlerPicker handlerPicker) {
    this.handlerPicker = handlerPicker;
    return this;
  }

  public InjectionContextBuilder adaptable(Object adaptable) {
    this.adaptable = adaptable;
    return this;
  }

  public InjectionContextBuilder propertyName(String propertyName) {
    this.propertyName = propertyName;
    return this;
  }

  public InjectionContextBuilder type(Type type) {
    this.type = type;
    return this;
  }

  public InjectionContextBuilder annotations(List<Class<? extends Annotation>> annotations) {
    this.annotations = annotations;
    return this;
  }

  public InjectionContextBuilder annotatedElement(AnnotatedElement annotatedElement) {
    this.annotatedElement = annotatedElement;
    return this;
  }

  public InjectionContextBuilder disposalCallbackRegistry(DisposalCallbackRegistry disposalCallbackRegistry) {
    this.disposalCallbackRegistry = disposalCallbackRegistry;
    return this;
  }

  public InjectionContext build() {
    return new ImmutableInjectionContext(injector, requestCatcher, handlerPicker, adaptable, propertyName, type, annotations, annotatedElement, disposalCallbackRegistry);
  }

}
