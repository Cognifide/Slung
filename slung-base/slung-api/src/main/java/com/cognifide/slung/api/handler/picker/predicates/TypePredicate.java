package com.cognifide.slung.api.handler.picker.predicates;

import com.google.common.base.Predicate;
import java.lang.reflect.Type;
import org.apache.commons.lang3.reflect.TypeUtils;

public class TypePredicate implements Predicate<Type> {

  private final Type type;

  public TypePredicate(Type type) {
    this.type = type;
  }

  @Override
  public boolean apply(Type handlerAcceptedType) {
    return TypeUtils.isAssignable(type, handlerAcceptedType);
  }

}
