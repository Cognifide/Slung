package com.cognifide.slung.slice.processor.old;

import com.cognifide.slung.api.context.handler.converter.StringToObjectConverter;
import com.cognifide.slung.slice.processor.common.PropertyNameFinder;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import java.lang.reflect.Field;
import org.apache.sling.api.resource.Resource;
import com.cognifide.slung.qualifier.Old;

public class OldResourceToValueFunction implements Function<Resource, Optional<Object>> {

  private final Field field;

  public OldResourceToValueFunction(Field field) {
    this.field = field;
  }

  @Override
  public Optional<Object> apply(Resource oldResource) {
    return Optional.fromNullable(useDefaultIfNull(getValueFrom(oldResource)));
  }

  private Object getValueFrom(Resource oldResource) {
    return oldResource.getValueMap().get(PropertyNameFinder.find(field));
  }

  private Object useDefaultIfNull(Object value) {
    return null == value ? new StringToObjectConverter(field, extractDefaultValue()).produce() : value;
  }

  private String[] extractDefaultValue() {
    return field.getAnnotation(Old.class).defaultValue();
  }

}
