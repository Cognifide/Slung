package com.cognifide.slung.component.filter;

import com.cognifide.slung.api.Constants;
import com.cognifide.slung.component.filter.configuration.ComponentCssClassesConfiguration;
import com.cognifide.slung.component.filter.configuration.ComponentFilterConfiguration;
import com.cognifide.slung.component.filter.css.CssClassesSelector;
import com.cognifide.slung.component.filter.response.ComponentResponse;
import com.cognifide.slung.helper.DiffHelper;
import com.cognifide.slung.helper.PathHelper;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingFilter;
import org.apache.felix.scr.annotations.sling.SlingFilterScope;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;

@SlingFilter(
    order = -1100,
    scope = SlingFilterScope.COMPONENT,
    label = Constants.LABEL_PREFIX + "Diff component filter",
    description = "When enabled add proper css classes in diff mode indicating if component was added, modified or removed.")
public class DiffComponentFilter implements Filter {

  static final String ENABLE_DIFF_COMPONENT_FILTER_PROPERTY_NAME = Constants.PROPERTY_PREFIX + "filter.enabled";

  static final boolean ENABLE_DIFF_COMPONENT_FILTER_PROPERTY_DEFAULT_VALUE = true;

  @Reference
  private ComponentFilterConfiguration componentFilterConfiguration;

  @Reference
  private ComponentCssClassesConfiguration cssClassesConfiguration;

  private CssClassesSelector cssClassesSelector;

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    cssClassesSelector = new CssClassesSelector(cssClassesConfiguration);
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    if (componentFilterConfiguration.isEnabled() && request instanceof SlingHttpServletRequest && response instanceof HttpServletResponse) {
      filter((SlingHttpServletRequest) request, (HttpServletResponse) response, chain);
    } else {
      chain.doFilter(request, response);
    }
  }

  private void filter(final SlingHttpServletRequest request, final HttpServletResponse response, final FilterChain chain) throws IOException, ServletException {
    final Resource resource = request.getResource();
    if (componentFilterConfiguration.canFilter(resource.getResourceType()) && isDiffRequest(request)) {
      final ComponentResponse componentResponse = new ComponentResponse(response);
      chain.doFilter(request, componentResponse);
      componentResponse.getWriter().flush();
      fillResponse(response, componentResponse, cssClassesSelector.select(request, resource));
    } else {
      chain.doFilter(request, response);
    }
  }

  private boolean isDiffRequest(SlingHttpServletRequest request) {
    return DiffHelper.isPreviewMode(request)
        && DiffHelper.hasDiffParameter(request)
        && PathHelper.resourcePathStartsWithRequestPath(request);
  }

  private void fillResponse(final ServletResponse response, final ComponentResponse componentResponse, final String cssClasses) throws UnsupportedEncodingException, IOException {
    final String characterEncoding = componentResponse.getCharacterEncoding();
    final boolean wrap = StringUtils.isNotBlank(cssClasses);
    final PrintWriter writer = response.getWriter();
    if (wrap) {
      writer.write("<span class=\"");
      writer.write(cssClasses);
      writer.write("\">");
    }
    writer.write(new String(componentResponse.getContent().toByteArray(), characterEncoding));
    if (wrap) {
      writer.write("</span>");
    }
    writer.flush();
  }

  @Override
  public void destroy() {
  }

}
