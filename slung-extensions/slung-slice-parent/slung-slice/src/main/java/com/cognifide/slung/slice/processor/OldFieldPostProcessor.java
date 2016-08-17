package com.cognifide.slung.slice.processor;

import com.cognifide.slice.mapper.api.processor.FieldPostProcessor;
import com.cognifide.slung.api.context.handler.seeker.OldResourceSeeker;
import com.cognifide.slung.api.util.Optionals;
import com.cognifide.slung.qualifier.Old;
import com.cognifide.slung.slice.processor.common.SlungFieldPostProcessor;
import com.cognifide.slung.slice.processor.old.OldResourceToValueFunction;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
import javax.servlet.ServletRequest;
import org.apache.sling.api.resource.Resource;

public class OldFieldPostProcessor implements FieldPostProcessor {

  private static final Collection<Class<? extends Annotation>> ANNOTATED_WITH = Collections.<Class<? extends Annotation>>singleton(Old.class);

  private final SlungFieldPostProcessor fieldPostProcessor;

  public OldFieldPostProcessor(ServletRequest request) {
    this.fieldPostProcessor = SlungFieldPostProcessor.newOldTypeFieldPostProcessor(ANNOTATED_WITH, request);
  }

  @Override
  public boolean accepts(Resource resource, Field field, Object value) {
    return fieldPostProcessor.accepts(resource, field, value);
  }

  @Override
  public Object processValue(Resource resource, Field field, Object value) {
    return Optionals.flatten(OldResourceSeeker.using(fieldPostProcessor.getRequest(), resource).seek()
        .transform(new OldResourceToValueFunction(field)))
        .orNull();
  }
}
