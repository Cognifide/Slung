package com.cognifide.slung.api.condition.analyzer;

import com.cognifide.slung.condition.Condition;
import com.google.common.base.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassToConditionFunction implements Function<Class<? extends Condition>, Condition> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ClassToConditionFunction.class);

  public static final Function<Class<? extends Condition>, Condition> INSTANCE = new ClassToConditionFunction();

  @Override
  public Condition apply(Class<? extends Condition> type) {
    Condition condition = FalseCondition.INSTANCE;
    try {
      condition = type.newInstance();
    } catch (InstantiationException | IllegalAccessException x) {
      LOGGER.error("Error while instantiating predicate.", x);
    }
    return condition;
  }

}
