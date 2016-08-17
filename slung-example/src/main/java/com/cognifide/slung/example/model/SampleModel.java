package com.cognifide.slung.example.model;

import com.cognifide.slice.mapper.annotation.JcrProperty;
import com.cognifide.slice.mapper.annotation.SliceResource;
import com.cognifide.slung.qualifier.Diff;

@SliceResource
public class SampleModel {

  @Diff
  @JcrProperty
  private String text;

  @Diff
  @JcrProperty("./nested/text")
  private String nestedText;

  public String getText() {
    return text;
  }

  public String getNestedText() {
    return nestedText;
  }

}
