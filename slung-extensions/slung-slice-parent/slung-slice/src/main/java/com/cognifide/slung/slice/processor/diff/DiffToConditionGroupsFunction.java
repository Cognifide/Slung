package com.cognifide.slung.slice.processor.diff;

import com.cognifide.slung.qualifier.ConditionGroup;
import com.cognifide.slung.qualifier.Diff;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import java.lang.reflect.Field;

public class DiffToConditionGroupsFunction implements Function<Field, Iterable<ConditionGroup>> {

  public static final Function<Field, Iterable<ConditionGroup>> INSTANCE = new DiffToConditionGroupsFunction();

  private DiffToConditionGroupsFunction() {
  }

  @Override
  public Iterable<ConditionGroup> apply(Field field) {
    return ImmutableList.copyOf(field.getAnnotation(Diff.class).conditionGroups());
  }

}
