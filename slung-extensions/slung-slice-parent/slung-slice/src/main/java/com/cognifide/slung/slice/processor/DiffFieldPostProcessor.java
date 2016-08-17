package com.cognifide.slung.slice.processor;

import com.cognifide.slice.mapper.annotation.Children;
import com.cognifide.slice.mapper.api.processor.FieldPostProcessor;
import com.cognifide.slung.api.context.handler.HandlerContextBuilder;
import com.cognifide.slung.api.context.handler.converter.StringToObjectConverter;
import com.cognifide.slung.api.handler.picker.HandlerPicker;
import com.cognifide.slung.qualifier.Diff;
import com.cognifide.slung.slice.processor.common.PropertyNameFinder;
import com.cognifide.slung.slice.processor.common.SlungFieldPostProcessor;
import com.google.common.base.Preconditions;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
import javax.servlet.ServletRequest;
import org.apache.sling.api.resource.Resource;

public class DiffFieldPostProcessor implements FieldPostProcessor {

  private static final Collection<Class<? extends Annotation>> ANNOTATED_WITH = Collections.<Class<? extends Annotation>>singleton(Diff.class);

  private static final Collection<Class<? extends Annotation>> NOT_ANNOTATED_WITH = Collections.<Class<? extends Annotation>>singleton(Children.class);

  private final SlungFieldPostProcessor fieldPostProcessor;

  private final HandlerPicker handlerPicker;

  public DiffFieldPostProcessor(ServletRequest request, HandlerPicker handlerPicker) {
    this.fieldPostProcessor = SlungFieldPostProcessor.newDiffTypeFieldPostProcessor(ANNOTATED_WITH, NOT_ANNOTATED_WITH, request);
    this.handlerPicker = Preconditions.checkNotNull(handlerPicker);
  }

  @Override
  public boolean accepts(Resource resource, Field field, Object value) {
    return fieldPostProcessor.accepts(resource, field, value);
  }

  @Override
  public Object processValue(Resource resource, Field field, Object value) {
    return handlerPicker.tryToProcessUsing(HandlerContextBuilder.from(fieldPostProcessor.getRequest())
        .resource(resource)
        .propertyName(PropertyNameFinder.find(field))
        .field(field)
        .value(value)
        .valueProducer(new StringToObjectConverter(field, extractDefaultValue(field)))
        .build());
  }

  private String[] extractDefaultValue(Field field) {
    return field.getAnnotation(Diff.class).defaultValue();
  }

}
