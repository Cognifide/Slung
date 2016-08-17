package com.cognifide.slung.sling.injector.function.resolver;

import java.lang.reflect.AnnotatedElement;
import java.util.Map;

public interface ValueResolver {

  Class<?> getType();

  void registerIn(Map<Class<?>, ValueResolver> resolvers);

  Object resolve(AnnotatedElement annotatedElement);
}
