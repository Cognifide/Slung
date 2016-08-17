package com.cognifide.slung.helper;

import java.util.Objects;
import javax.annotation.Nonnull;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;

/**
 * Path utilities.
 */
public final class PathHelper {

  private PathHelper() {
    throw new AssertionError();
  }

  /**
   * Checks whether request's resource path start with request path
   *
   * @param request non null request, which has not null resource, when asked using {@link SlingHttpServletRequest#getResource()}
   * @return {@code true} when request's resource path starts with request path, {@code false} otherwise or when
   * request's resource path or request path is null
   */
  public static boolean resourcePathStartsWithRequestPath(@Nonnull final SlingHttpServletRequest request) {
    Objects.requireNonNull(request);
    return resourcePathStartsWithRequestPath(request, request.getResource());
  }

  /**
   * Checks whether resource path starts with request path.
   *
   * @param request non null request
   * @param resource non null resource
   * @return {@code true} when resource path starts with request path, {@code false} otherwise or when resource path or
   * request path is null
   */
  public static boolean resourcePathStartsWithRequestPath(@Nonnull final SlingHttpServletRequest request, @Nonnull final Resource resource) {
    Objects.requireNonNull(request);
    Objects.requireNonNull(resource);
    final String resourcePath = resource.getPath();
    final String requestPath = request.getRequestPathInfo().getResourcePath();
    return null != resourcePath && null != requestPath && resourcePath.startsWith(requestPath);
  }
}
