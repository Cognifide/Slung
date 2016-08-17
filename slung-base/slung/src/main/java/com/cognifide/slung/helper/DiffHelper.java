package com.cognifide.slung.helper;

import com.day.cq.commons.DiffService;
import com.day.cq.wcm.api.WCMMode;
import javax.annotation.Nullable;
import javax.servlet.ServletRequest;
import org.apache.jackrabbit.JcrConstants;
import org.apache.sling.api.resource.Resource;

/**
 * Diff mode utilities.
 */
public final class DiffHelper {

  private DiffHelper() {
    throw new AssertionError();
  }

  /**
   * Checks whether current request is in {@code WCMMode.PREVIEW} mode.
   *
   * @param request current request, might be null
   * @return {@code true} if request is in {@code WCMMode.PREVIEW} mode, {@code false} otherwise and {@code false} when
   * request is null
   */
  public static boolean isPreviewMode(@Nullable final ServletRequest request) {
    return null != request ? WCMMode.PREVIEW == WCMMode.fromRequest(request) : false;
  }

  /**
   * Checks whether current request has non null and non empty {@link DiffService.REQUEST_PARAM_DIFF_TO} parameter.
   *
   * @param request current request, might be null
   * @return {@code true} if request has non null or non empty {@link DiffService.REQUEST_PARAM_DIFF_TO} parameter,
   * {@code false} otherwise and {@code false} when request is null
   */
  public static boolean hasDiffParameter(@Nullable final ServletRequest request) {
    return null != request ? null != request.getParameter(DiffService.REQUEST_PARAM_DIFF_TO) : false;
  }

  /**
   * Checks whether given resource is frozen node.
   *
   * @param resource resource, might be null
   * @return {@code true} when resource {@link JcrConstants.JCR_PRIMARYTYPE} is
   * {@link JcrConstants.NT_FROZENNODE}, {@code false} otherwise and {@code false} when resource is null
   */
  public static boolean isFrozenNode(@Nullable final Resource resource) {
    return null != resource ? JcrConstants.NT_FROZENNODE.equals(resource.getValueMap().get(JcrConstants.JCR_PRIMARYTYPE, String.class)) : false;
  }
}
