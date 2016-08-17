package com.cognifide.slung.example.components.material.card;

import com.cognifide.slice.api.model.InitializableModel;
import com.cognifide.slice.api.qualifier.CurrentResourcePath;
import com.cognifide.slice.mapper.annotation.JcrProperty;
import com.cognifide.slice.mapper.annotation.SliceResource;
import com.cognifide.slice.mapper.annotation.Unescaped;
import com.cognifide.slung.qualifier.Diff;
import com.cognifide.slung.qualifier.Old;
import com.google.inject.Inject;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@ToString
@SliceResource
public class SquareCardModel implements InitializableModel {

  @Diff
  @JcrProperty("image")
  private String imageCss;

  @Old
  @JcrProperty("image/fileReference")
  private String image;

  @Diff
  @JcrProperty
  private String title;

  @Diff
  @Unescaped
  @JcrProperty
  private String description;

  @Diff
  @JcrProperty
  private String link;

  @Diff
  @JcrProperty
  private String label;

  @CurrentResourcePath
  @Inject
  private String currentResourcePath;

  @Override
  public void afterCreated() {
    log.warn("{} created from {}", this, currentResourcePath);
  }

}
