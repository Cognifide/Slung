package com.cognifide.slung.handler.picker.strategy.ranking.table;

import java.util.Comparator;

class ClassComparator implements Comparator<Class<?>> {

  public static final Comparator<Class<?>> INSTANCE = new ClassComparator();
  
  private ClassComparator() {
  }
  
  @Override
  public int compare(Class<?> o1, Class<?> o2) {
    return Sum.class.equals(o1) || Sum.class.equals(o2) ? handleSum(o1, o2) : o1.toString().compareTo(o2.toString());
  }

  public int handleSum(Class<?> o1, Class<?> o2) {
    return Sum.class.equals(o1) && Sum.class.equals(o2) ? 0 : (Sum.class.equals(o1) ? 1 : -1);
  }

}
