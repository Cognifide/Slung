package com.cognifide.slung.example.model;

import com.cognifide.slice.mapper.annotation.JcrProperty;
import com.cognifide.slice.mapper.annotation.SliceResource;
import com.cognifide.slung.qualifier.Diff;

@SliceResource
public class LinkModel {

  @Diff
  @JcrProperty
  private String title;

  @Diff
  @JcrProperty
  private String url;

  public String getTitle() {
    return title;
  }

  public String getUrl() {
    return url;
  }

}
