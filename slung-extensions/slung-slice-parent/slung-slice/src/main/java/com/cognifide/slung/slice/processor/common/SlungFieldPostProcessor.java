package com.cognifide.slung.slice.processor.common;

import com.cognifide.slung.api.condition.ConditionContextBuilder;
import com.cognifide.slung.api.condition.analyzer.ConditionAnalyzer;
import com.cognifide.slung.qualifier.ConditionGroup;
import com.cognifide.slung.slice.processor.diff.DiffToConditionGroupsFunction;
import com.cognifide.slung.slice.processor.diff.DiffToDefaultValueFunction;
import com.cognifide.slung.slice.processor.old.OldToConditionGroupsFunction;
import com.cognifide.slung.slice.processor.old.OldToDefaultValueFunction;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import javax.servlet.ServletRequest;
import org.apache.sling.api.resource.Resource;

public class SlungFieldPostProcessor {

  private final Collection<Class<? extends Annotation>> annotatedWith;

  private final Collection<Class<? extends Annotation>> notAnnotatedWith;

  private final ServletRequest request;

  private final Function<Field, Iterable<ConditionGroup>> conditionGroupsFunction;

  private final Function<Field, Object> defaultValueFunction;

  private SlungFieldPostProcessor(Collection<Class<? extends Annotation>> annotatedWith, ServletRequest request, Function<Field, Iterable<ConditionGroup>> conditionGroupsFunction, Function<Field, Object> defaultValueFunction) {
    this(annotatedWith, Collections.<Class<? extends Annotation>>emptyList(), request, conditionGroupsFunction, defaultValueFunction);
  }

  private SlungFieldPostProcessor(Collection<Class<? extends Annotation>> annotatedWith, Collection<Class<? extends Annotation>> notAnnotatedWith, ServletRequest request, Function<Field, Iterable<ConditionGroup>> conditionGroupsFunction, Function<Field, Object> defaultValueFunction) {
    this.annotatedWith = Objects.requireNonNull(annotatedWith);
    this.notAnnotatedWith = Objects.requireNonNull(notAnnotatedWith);
    this.request = request;
    this.conditionGroupsFunction = Objects.requireNonNull(conditionGroupsFunction);
    this.defaultValueFunction = Objects.requireNonNull(defaultValueFunction);
  }

  public ServletRequest getRequest() {
    return request;
  }

  public boolean accepts(final Resource resource, final Field field, final Object value) {
    final Predicate<Class<? extends Annotation>> predicate = new AnnotationPredicate(field);
    return Iterables.all(annotatedWith, predicate)
        && !Iterables.any(notAnnotatedWith, predicate)
        && meetsRequirements(resource, field, value);
  }

  private boolean meetsRequirements(final Resource resource, final Field field, final Object value) {
    return new ConditionAnalyzer().accepts(ConditionContextBuilder.from(request)
        .resource(resource)
        .annotatedElement(field)
        .value(value)
        .defaultValue(defaultValueFunction.apply(field))
        .conditionGroups(conditionGroupsFunction.apply(field))
        .build());
  }

  public static SlungFieldPostProcessor newDiffTypeFieldPostProcessor(Collection<Class<? extends Annotation>> annotatedWith, ServletRequest request) {
    return new SlungFieldPostProcessor(annotatedWith, request, DiffToConditionGroupsFunction.INSTANCE, DiffToDefaultValueFunction.INSTANCE);
  }

  public static SlungFieldPostProcessor newDiffTypeFieldPostProcessor(Collection<Class<? extends Annotation>> annotatedWith, Collection<Class<? extends Annotation>> notAnnotatedWith, ServletRequest request) {
    return new SlungFieldPostProcessor(annotatedWith, notAnnotatedWith, request, DiffToConditionGroupsFunction.INSTANCE, DiffToDefaultValueFunction.INSTANCE);
  }

  public static SlungFieldPostProcessor newOldTypeFieldPostProcessor(Collection<Class<? extends Annotation>> annotatedWith, ServletRequest request) {
    return new SlungFieldPostProcessor(annotatedWith, request, OldToConditionGroupsFunction.INSTANCE, OldToDefaultValueFunction.INSTANCE);
  }
}
