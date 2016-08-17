package com.cognifide.slung.qualifier;

import com.cognifide.slung.condition.Condition;
import com.cognifide.slung.condition.Conjuction;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ConditionGroup {

  /**
   * 
   * @return 
   */
  Conjuction conjuction() default Conjuction.OR;

  /**
   * 
   * @return 
   */
  Class<? extends Condition>[] conditions() default {};
}
