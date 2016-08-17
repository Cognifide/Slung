package com.cognifide.slung.qualifier;

import com.cognifide.slung.condition.Conjuction;
import com.cognifide.slung.condition.base.FrozenCondition;
import com.cognifide.slung.condition.base.InPreviewModeCondition;
import com.cognifide.slung.condition.base.SameRequestPathCondition;
import com.cognifide.slung.condition.base.WithDiffParameterCondition;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Diff {

  /**
   * 
   * @return 
   */
  String propertyName() default "";

  /**
   * 
   * @return 
   */
  String handlerName() default "";

  /**
   * 
   * @return 
   */
  String[] defaultValue() default {};

  /**
   * 
   * @return 
   */
  ConditionGroup[] conditionGroups() default {
    @ConditionGroup(conjuction = Conjuction.AND, conditions = {InPreviewModeCondition.class, WithDiffParameterCondition.class}),
    @ConditionGroup(conjuction = Conjuction.OR, conditions = {FrozenCondition.class, SameRequestPathCondition.class})
  };

}
