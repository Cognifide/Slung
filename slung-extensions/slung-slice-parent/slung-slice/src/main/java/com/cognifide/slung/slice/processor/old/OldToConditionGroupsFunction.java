package com.cognifide.slung.slice.processor.old;

import com.cognifide.slung.qualifier.ConditionGroup;
import com.cognifide.slung.qualifier.Old;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import java.lang.reflect.Field;

public class OldToConditionGroupsFunction implements Function<Field, Iterable<ConditionGroup>> {

  public static final Function<Field, Iterable<ConditionGroup>> INSTANCE = new OldToConditionGroupsFunction();

  private OldToConditionGroupsFunction() {
  }

  @Override
  public Iterable<ConditionGroup> apply(Field field) {
    return ImmutableList.copyOf(field.getAnnotation(Old.class).conditionGroups());
  }

}
