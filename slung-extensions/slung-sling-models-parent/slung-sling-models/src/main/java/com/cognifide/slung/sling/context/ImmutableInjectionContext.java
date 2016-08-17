package com.cognifide.slung.sling.context;

import com.cognifide.slung.api.handler.picker.HandlerPicker;
import com.cognifide.slung.sling.filter.RequestCatcher;
import static com.google.common.base.Preconditions.checkNotNull;
import com.google.common.collect.ImmutableList;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import org.apache.sling.models.spi.DisposalCallbackRegistry;
import org.apache.sling.models.spi.Injector;

public class ImmutableInjectionContext implements InjectionContext {

  private final RequestCatcher requestCatcher;

  private final Injector injector;

  private final HandlerPicker handlerPicker;

  private final Object adaptable;

  private final String propertyName;

  private final Type type;

  private final List<Class<? extends Annotation>> annotations;

  private final AnnotatedElement annotatedElement;

  private final DisposalCallbackRegistry disposalCallbackRegistry;

  ImmutableInjectionContext(Injector injector, RequestCatcher requestCatcher, HandlerPicker handlerPicker, Object adaptable, String propertyName, Type type, List<Class<? extends Annotation>> annotations, AnnotatedElement annotatedElement, DisposalCallbackRegistry disposalCallbackRegistry) {
    this.requestCatcher = checkNotNull(requestCatcher);
    this.injector = injector;
    this.handlerPicker = handlerPicker;
    this.adaptable = checkNotNull(adaptable);
    this.propertyName = propertyName;
    this.type = checkNotNull(type);
    this.annotations = ImmutableList.copyOf(annotations);
    this.annotatedElement = checkNotNull(annotatedElement);
    this.disposalCallbackRegistry = checkNotNull(disposalCallbackRegistry);
  }

  @Override
  public RequestCatcher getRequestCatcher() {
    return requestCatcher;
  }

  @Override
  public Injector getInjector() {
    return injector;
  }

  @Override
  public HandlerPicker getHandlerPicker() {
    return handlerPicker;
  }

  @Override
  public Object getAdaptable() {
    return adaptable;
  }

  @Override
  public String getProperyName() {
    return propertyName;
  }

  @Override
  public Type getType() {
    return type;
  }

  @Override
  public Iterable<Class<? extends Annotation>> getAnnotations() {
    return Collections.unmodifiableList(annotations);
  }

  @Override
  public AnnotatedElement getAnnotatedElement() {
    return annotatedElement;
  }

  @Override
  public DisposalCallbackRegistry getDisposalCallbackRegistry() {
    return disposalCallbackRegistry;
  }

}
