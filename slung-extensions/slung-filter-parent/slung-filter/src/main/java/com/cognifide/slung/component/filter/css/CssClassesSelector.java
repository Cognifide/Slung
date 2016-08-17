package com.cognifide.slung.component.filter.css;

import com.cognifide.slung.api.configuration.CssClassesConfiguration;
import com.cognifide.slung.api.context.handler.seeker.OldResourceSeeker;
import com.cognifide.slung.helper.DiffHelper;
import com.google.common.base.Joiner;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;

@AllArgsConstructor
public class CssClassesSelector {

  private static final Joiner CSS_CLASSES_JOINER = Joiner.on(StringUtils.SPACE).skipNulls();

  private final CssClassesConfiguration cssClassesConfiguration;

  public String select(final SlingHttpServletRequest request, final Resource resource) {
    return wasResourceRemoved(resource)
        ? CSS_CLASSES_JOINER.join(cssClassesConfiguration.getRemovedCssClasses())
        : OldResourceSeeker.using(request, resource).seek()
        .transform(new CssClassesFunction(cssClassesConfiguration, resource, CSS_CLASSES_JOINER))
        .or(new AddedCssClassesSupplier(cssClassesConfiguration, CSS_CLASSES_JOINER));
  }

  private boolean wasResourceRemoved(final Resource resource) {
    return DiffHelper.isFrozenNode(resource);
  }

}
