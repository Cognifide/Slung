package com.cognifide.slung.sling.context;

import com.cognifide.slung.api.handler.picker.HandlerPicker;
import com.cognifide.slung.sling.filter.RequestCatcher;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Type;
import org.apache.sling.models.spi.DisposalCallbackRegistry;
import org.apache.sling.models.spi.Injector;

public interface InjectionContext {

  RequestCatcher getRequestCatcher();

  Injector getInjector();

  HandlerPicker getHandlerPicker();

  Object getAdaptable();

  String getProperyName();

  Type getType();

  Iterable<Class<? extends Annotation>> getAnnotations();

  AnnotatedElement getAnnotatedElement();

  DisposalCallbackRegistry getDisposalCallbackRegistry();
}
