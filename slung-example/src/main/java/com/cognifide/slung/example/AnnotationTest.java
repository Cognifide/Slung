package com.cognifide.slung.example;

import com.cognifide.slice.mapper.annotation.JcrProperty;
import com.cognifide.slung.qualifier.Diff;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

public class AnnotationTest {

  @Diff
  private int a;

  @Diff
  private int[] aa;

  @Diff
  private Integer aaa;

  @Diff
  private Integer[] aaaa;

  @Diff
  private Collection<Integer> aaaaa;

  @Diff
  private Set<Integer> set;

  @Diff
  private SortedSet<Integer> sortedSet;

  @Diff
  private List<Integer> list;

  @Diff
  private String s;

  @Diff
  private String[] ss;

  @Diff
  private Collection<String> sss;
  
  @Diff
  @JcrProperty("a")
  private String abc;

}
