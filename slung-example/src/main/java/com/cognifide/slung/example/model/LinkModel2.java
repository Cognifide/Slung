package com.cognifide.slung.example.model;

import com.cognifide.slung.qualifier.Diff;
import javax.inject.Inject;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class LinkModel2 {

  @Diff
  @Inject
  private String title;

  @Diff
  @Inject
  private String url;

  public String getTitle() {
    return title;
  }

  public String getUrl() {
    return url;
  }
}
