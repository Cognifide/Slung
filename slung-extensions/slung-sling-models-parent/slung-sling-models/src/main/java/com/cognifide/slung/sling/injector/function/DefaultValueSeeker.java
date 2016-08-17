package com.cognifide.slung.sling.injector.function;

import com.cognifide.slung.api.context.handler.ValueProducer;
import com.cognifide.slung.sling.context.InjectionContext;
import com.cognifide.slung.sling.injector.function.resolver.BooleanClassArrayResolver;
import com.cognifide.slung.sling.injector.function.resolver.BooleanClassResolver;
import com.cognifide.slung.sling.injector.function.resolver.BooleanTypeArrayResolver;
import com.cognifide.slung.sling.injector.function.resolver.BooleanTypeResolver;
import com.cognifide.slung.sling.injector.function.resolver.DoubleClassArrayResolver;
import com.cognifide.slung.sling.injector.function.resolver.DoubleClassResolver;
import com.cognifide.slung.sling.injector.function.resolver.DoubleTypeArrayResolver;
import com.cognifide.slung.sling.injector.function.resolver.DoubleTypeResolver;
import com.cognifide.slung.sling.injector.function.resolver.FloatArrayClassResolver;
import com.cognifide.slung.sling.injector.function.resolver.FloatClassResolver;
import com.cognifide.slung.sling.injector.function.resolver.FloatTypeArrayResolver;
import com.cognifide.slung.sling.injector.function.resolver.FloatTypeResolver;
import com.cognifide.slung.sling.injector.function.resolver.IntegerClassArrayResolver;
import com.cognifide.slung.sling.injector.function.resolver.IntegerClassResolver;
import com.cognifide.slung.sling.injector.function.resolver.IntegerTypeArrayResolver;
import com.cognifide.slung.sling.injector.function.resolver.IntegerTypeResolver;
import com.cognifide.slung.sling.injector.function.resolver.LongClassArrayResolver;
import com.cognifide.slung.sling.injector.function.resolver.LongClassResolver;
import com.cognifide.slung.sling.injector.function.resolver.LongTypeArrayResolver;
import com.cognifide.slung.sling.injector.function.resolver.LongTypeResolver;
import com.cognifide.slung.sling.injector.function.resolver.ShortClassArrayResolver;
import com.cognifide.slung.sling.injector.function.resolver.ShortClassResolver;
import com.cognifide.slung.sling.injector.function.resolver.ShortTypeArrayResolver;
import com.cognifide.slung.sling.injector.function.resolver.ShortTypeResolver;
import com.cognifide.slung.sling.injector.function.resolver.StringArrayResolver;
import com.cognifide.slung.sling.injector.function.resolver.StringResolver;
import com.cognifide.slung.sling.injector.function.resolver.ValueResolver;
import com.google.common.collect.Maps;
import java.util.Map;
import org.apache.commons.lang3.reflect.TypeUtils;

public class DefaultValueSeeker implements ValueProducer {

  private static final Map<Class<?>, ValueResolver> RESOLVERS = Maps.newIdentityHashMap();

  static {
    BooleanClassArrayResolver.INSTANCE.registerIn(RESOLVERS);
    BooleanClassResolver.INSTANCE.registerIn(RESOLVERS);
    BooleanTypeArrayResolver.INSTANCE.registerIn(RESOLVERS);
    BooleanTypeResolver.INSTANCE.registerIn(RESOLVERS);
    DoubleClassArrayResolver.INSTANCE.registerIn(RESOLVERS);
    DoubleClassResolver.INSTANCE.registerIn(RESOLVERS);
    DoubleTypeArrayResolver.INSTANCE.registerIn(RESOLVERS);
    DoubleTypeResolver.INSTANCE.registerIn(RESOLVERS);
    FloatArrayClassResolver.INSTANCE.registerIn(RESOLVERS);
    FloatClassResolver.INSTANCE.registerIn(RESOLVERS);
    FloatTypeArrayResolver.INSTANCE.registerIn(RESOLVERS);
    FloatTypeResolver.INSTANCE.registerIn(RESOLVERS);
    IntegerClassArrayResolver.INSTANCE.registerIn(RESOLVERS);
    IntegerClassResolver.INSTANCE.registerIn(RESOLVERS);
    IntegerTypeArrayResolver.INSTANCE.registerIn(RESOLVERS);
    IntegerTypeResolver.INSTANCE.registerIn(RESOLVERS);
    LongClassArrayResolver.INSTANCE.registerIn(RESOLVERS);
    LongClassResolver.INSTANCE.registerIn(RESOLVERS);
    LongTypeArrayResolver.INSTANCE.registerIn(RESOLVERS);
    LongTypeResolver.INSTANCE.registerIn(RESOLVERS);
    ShortClassArrayResolver.INSTANCE.registerIn(RESOLVERS);
    ShortClassResolver.INSTANCE.registerIn(RESOLVERS);
    ShortTypeArrayResolver.INSTANCE.registerIn(RESOLVERS);
    ShortTypeResolver.INSTANCE.registerIn(RESOLVERS);
    StringArrayResolver.INSTANCE.registerIn(RESOLVERS);
    StringResolver.INSTANCE.registerIn(RESOLVERS);
  }

  private final InjectionContext injectionContext;

  public DefaultValueSeeker(InjectionContext injectionContext) {
    this.injectionContext = injectionContext;
  }

  @Override
  public Object produce() {
    final Class<?> type = TypeUtils.getRawType(injectionContext.getType(), Class.class);
    return type != null && RESOLVERS.containsKey(type) ? RESOLVERS.get(type).resolve(injectionContext.getAnnotatedElement()) : null;
  }
}
