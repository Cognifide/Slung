package com.cognifide.slung.example.components.material.card;

import com.cognifide.slice.api.model.InitializableModel;
import com.cognifide.slice.api.qualifier.CurrentResourcePath;
import com.cognifide.slice.mapper.annotation.JcrProperty;
import com.cognifide.slice.mapper.annotation.SliceResource;
import com.cognifide.slung.qualifier.Diff;
import com.google.inject.Inject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@ToString
@SliceResource
public class EventCardModel implements InitializableModel {

  @Getter(AccessLevel.NONE)
  private final SimpleDateFormat dateParser = new SimpleDateFormat("dd/MM/yy");

  @Getter(AccessLevel.NONE)
  private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMMM,yyyy", Locale.ENGLISH);

  @Diff
  @JcrProperty
  private String description;

  @Diff
  @JcrProperty
  private String date;

  @Diff
  @JcrProperty
  private String time;

  @Diff
  @JcrProperty
  private String label;

  @Diff
  @JcrProperty
  private String icon;

  @CurrentResourcePath
  @Inject
  private String currentResourcePath;

  public String getDate() {
    String result = date;
    try {
      if (null != date) {
        result = dateFormat.format(dateParser.parse(date));
      }
    } catch (ParseException x) {
      log.error("Error while parsing date " + date, x);
    }
    return result;
  }

  @Override
  public void afterCreated() {
    log.warn("{} created from {}", this, currentResourcePath);
  }
}
