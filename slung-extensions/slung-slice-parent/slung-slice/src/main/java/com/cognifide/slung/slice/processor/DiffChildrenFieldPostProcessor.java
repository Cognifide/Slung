package com.cognifide.slung.slice.processor;

import com.cognifide.slice.api.provider.ModelProvider;
import com.cognifide.slice.mapper.annotation.Children;
import com.cognifide.slice.mapper.api.processor.FieldPostProcessor;
import com.cognifide.slung.api.context.handler.seeker.OldResourceSeeker;
import com.cognifide.slung.api.util.Optionals;
import com.cognifide.slung.qualifier.Diff;
import com.cognifide.slung.slice.processor.children.OldResourceToChildrenFunction;
import com.cognifide.slung.slice.processor.common.SlungFieldPostProcessor;
import com.google.common.base.Optional;
import static com.google.common.base.Preconditions.checkNotNull;
import com.google.common.collect.ImmutableList;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;
import javax.servlet.ServletRequest;
import org.apache.sling.api.resource.Resource;

public class DiffChildrenFieldPostProcessor implements FieldPostProcessor {

  private static final Collection<Class<? extends Annotation>> ANNOTATED_WITH = ImmutableList.of(Diff.class, Children.class);

  private final SlungFieldPostProcessor fieldPostProcessor;

  private final ModelProvider modelProvider;

  public DiffChildrenFieldPostProcessor(ServletRequest request, ModelProvider modelProvider) {
    this.fieldPostProcessor = SlungFieldPostProcessor.newDiffTypeFieldPostProcessor(ANNOTATED_WITH, request);
    this.modelProvider = checkNotNull(modelProvider);
  }

  @Override
  public boolean accepts(Resource resource, Field field, Object value) {
    return fieldPostProcessor.accepts(resource, field, value);
  }

  @Override
  public Object processValue(final Resource resource, final Field field, final Object value) {
    return Optionals.flatten(OldResourceSeeker.using(fieldPostProcessor.getRequest(), resource).seek().transform(new OldResourceToChildrenFunction(resource, field, value, modelProvider)))
        .or(Optional.fromNullable(value))
        .orNull();
  }

}
