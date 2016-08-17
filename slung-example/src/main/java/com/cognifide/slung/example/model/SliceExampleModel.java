package com.cognifide.slung.example.model;

import com.cognifide.slice.api.model.InitializableModel;
import com.cognifide.slice.api.qualifier.CurrentResourcePath;
import com.cognifide.slice.mapper.annotation.Children;
import com.cognifide.slice.mapper.annotation.JcrProperty;
import com.cognifide.slice.mapper.annotation.SliceResource;
import com.cognifide.slung.condition.Conjuction;
import com.cognifide.slung.condition.base.InPreviewModeCondition;
import com.cognifide.slung.condition.base.WithDiffParameterCondition;
import com.cognifide.slung.example.condition.RedCondition;
import com.cognifide.slung.qualifier.ConditionGroup;
import com.cognifide.slung.qualifier.Diff;
import com.google.inject.Inject;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SliceResource
public class SliceExampleModel implements InitializableModel {

  private static final Logger LOGGER = LoggerFactory.getLogger(SliceExampleModel.class);

  private static final String NUMBER_DEFAULT_VALUE_AS_STRING = "999";

  private static final Integer NUMBER_DEFAULT_VALUE = Integer.parseInt(NUMBER_DEFAULT_VALUE_AS_STRING);

  @Diff(conditionGroups = @ConditionGroup(conjuction = Conjuction.AND, conditions = {InPreviewModeCondition.class, WithDiffParameterCondition.class, RedCondition.class}))
  @JcrProperty
  private String redText;

  @Diff
  @JcrProperty("elements")
  private List<String> elementsAsList;

  @Diff
  @JcrProperty("./nested/text")
  private String nestedText;

  @Diff(handlerName = "pin")
  @JcrProperty
  private int pin;

  @JcrProperty
  private Integer number;

  @Diff(defaultValue = NUMBER_DEFAULT_VALUE_AS_STRING)
  @JcrProperty("number")
  private String numberAsString;

  @JcrProperty
  private boolean condition;

  @Diff
  @JcrProperty("condition")
  private String conditionAsString;

  @Diff
  @Children(LinkModel.class)
  @JcrProperty("")
  private List<LinkModel> links;

  @CurrentResourcePath
  @Inject
  private String currentResourcePath;

  public String getRedText() {
    return redText;
  }

  public List<String> getElementsAsList() {
    return null == elementsAsList ? Collections.<String>emptyList() : Collections.unmodifiableList(elementsAsList);
  }

  public String getNestedText() {
    return nestedText;
  }

  public Integer getPin() {
    return pin;
  }

  public Integer getNumber() {
    return null == number ? NUMBER_DEFAULT_VALUE : number;
  }

  public String getNumberAsString() {
    return numberAsString;
  }

  public boolean isCondition() {
    return condition;
  }

  public String getConditionAsString() {
    return conditionAsString;
  }

  public List<LinkModel> getLinks() {
    return Collections.unmodifiableList(links);
  }

  @Override
  public void afterCreated() {
    LOGGER.warn("{}", currentResourcePath);
  }
}
