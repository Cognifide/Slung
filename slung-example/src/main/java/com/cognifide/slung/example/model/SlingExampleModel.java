package com.cognifide.slung.example.model;

import com.cognifide.slung.example.condition.RedCondition;
import com.cognifide.slung.qualifier.ConditionGroup;
import com.cognifide.slung.qualifier.Diff;
import com.cognifide.slung.qualifier.Old;
import com.cognifide.slung.sling.injector.Injectors;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Source;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SlingExampleModel {

  private static final Logger LOGGER = LoggerFactory.getLogger(SlingExampleModel.class);

  private static final String IMAGE_DEFAULT_PATH = "/content/dam/projects/shapes/cover";

  private final Resource resource;

  @Diff
  @Inject
  private String text;

  @Diff(conditionGroups = @ConditionGroup(conditions = RedCondition.class))
  @Inject
  private String redText;

  @Diff
  @Inject
  @Default(values = {"element1", "element2", "element3"})
  private String[] elements;

  @Diff
  @Inject
  @Default(values = {"list-element1", "list-element2", "list-element3"})
  @Named("elements")
  private List<String> elementsAsList;

  @Diff
  @Inject
  @Named("./nested/text")
  private String nestedText;

  @Diff
  @Inject
  private String richText;

  @Diff(handlerName = "pin")
  @Inject
  private int pin;

  @Inject
  @Default(intValues = 999)
  private Integer number;

  @Diff
  @Inject
  @Named("number")
  @Default(intValues = 999)
  private String numberAsString;

  @Inject
  private boolean condition;

  @Diff
  @Inject
  @Named("condition")
  private String conditionAsString;

  @Old
  @Inject
  @Named("image/fileReference")
  @Default(values = IMAGE_DEFAULT_PATH)
  private String imagePath;

  @Diff
  @Inject
  @Named("image")
  @Default(values = IMAGE_DEFAULT_PATH)
  private String cssClasses;

  @Old
  @Inject
  @Named("image2/fileReference")
  private String imagePath2;

  @Diff
  @Inject
  @Named("image2")
  private String cssClasses2;

  @Diff
  @Source(Injectors.DIFF_CHILDREN_INJECTOR_NAME)
  @ChildResource(injectionStrategy = InjectionStrategy.OPTIONAL)
  private List<LinkModel2> links;

  public SlingExampleModel(Resource resource) {
    this.resource = resource;
  }

  public String getText() {
    return text;
  }

  public String getRedText() {
    return redText;
  }

  public String[] getElements() {
    return elements;
  }

  public List<String> getElementsAsList() {
    return elementsAsList;
  }

  public String getNestedText() {
    return nestedText;
  }

  public String getRichText() {
    return richText;
  }

  public Integer getPin() {
    return pin;
  }

  public Integer getNumber() {
    return number;
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

  public String getImagePath() {
    return imagePath;
  }

  public String getCssClasses() {
    return cssClasses;
  }

  public String getImagePath2() {
    return null == imagePath2 ? "" : imagePath2;
  }

  public String getCssClasses2() {
    return cssClasses2;
  }

  public List<LinkModel2> getLinks() {
    return null == links ? Collections.<LinkModel2>emptyList() : Collections.unmodifiableList(links);
  }

  @PostConstruct
  public void afterCreated() {
    LOGGER.warn("{}", resource.getPath());
  }
}
