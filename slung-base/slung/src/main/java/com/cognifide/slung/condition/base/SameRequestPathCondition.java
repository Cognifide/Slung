package com.cognifide.slung.condition.base;

import com.cognifide.slung.condition.Condition;
import com.cognifide.slung.condition.ConditionContext;
import com.cognifide.slung.helper.PathHelper;
import javax.annotation.Nonnull;
import javax.servlet.ServletRequest;
import org.apache.sling.api.SlingHttpServletRequest;

/**
 * Condition used in {@link ConditionGroup}. Checks if request and resource originate from the same path. This condition
 * protects from situation when external page resources are used on current page, as they would be probably highlighted,
 * because external page might not have desired version.
 *
 * For example imagen situation when we have configuration page without any version and home page with version 1.0, that
 * uses configuration page. Author requests to differ home page between current page version and version 1.0. In that
 * situation components from configuration page would be asked for version 1.0, which does not exists, thus it would be
 * highlighted as new on that page, which may be misleading.
 */
public class SameRequestPathCondition implements Condition {

  @Override
  public boolean test(@Nonnull final ConditionContext conditionContext) {
    ServletRequest request = conditionContext.getRequest();
    return request instanceof SlingHttpServletRequest
        ? PathHelper.resourcePathStartsWithRequestPath((SlingHttpServletRequest) request, conditionContext.getResource())
        : false;
  }

}
