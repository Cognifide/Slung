package com.cognifide.slung.example.components.material.list;

import com.cognifide.slice.api.model.InitializableModel;
import com.cognifide.slice.api.qualifier.CurrentResourcePath;
import com.cognifide.slice.mapper.annotation.JcrProperty;
import com.cognifide.slice.mapper.annotation.SliceResource;
import com.cognifide.slung.qualifier.Diff;
import com.google.inject.Inject;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@ToString
@SliceResource
public class BasicListModel implements InitializableModel {

  @CurrentResourcePath
  @Inject
  private String currentResourcePath;

  @Diff
  @JcrProperty
  private String topic;

  @Diff
  @JcrProperty
  private String[] items;

  @Override
  public void afterCreated() {
    log.warn("{} created from {}", this, currentResourcePath);
  }

}
