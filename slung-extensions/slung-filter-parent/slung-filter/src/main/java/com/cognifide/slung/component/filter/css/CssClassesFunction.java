package com.cognifide.slung.component.filter.css;

import com.cognifide.slung.api.configuration.CssClassesConfiguration;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.jackrabbit.JcrConstants;
import org.apache.sling.api.resource.Resource;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CssClassesFunction implements Function<Resource, String> {

  private final CssClassesConfiguration cssClassesConfiguration;

  private final Resource resource;

  private final Joiner joiner;

  @Override
  public String apply(Resource oldResource) {
    return wasResourceNotModified(oldResource)
        ? StringUtils.EMPTY
        : joiner.join(cssClassesConfiguration.getModifiedCssClasses());
  }

  private boolean wasResourceNotModified(final Resource oldResource) {
    final Date lastModified = findLastModified(resource);
    final Date oldLastModified = findLastModified(oldResource);
    return (null == lastModified && null == oldLastModified)
        || (null != lastModified && null != oldLastModified && lastModified.equals(oldLastModified));
  }

  private Date findLastModified(final Resource resource) {
    return resource.getValueMap().get(JcrConstants.JCR_LASTMODIFIED, Date.class);
  }
}
