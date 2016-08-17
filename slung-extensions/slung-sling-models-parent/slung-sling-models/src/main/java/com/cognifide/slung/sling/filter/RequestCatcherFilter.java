package com.cognifide.slung.sling.filter;

import com.cognifide.slung.api.Constants;
import com.google.common.base.Optional;
import com.google.common.collect.Queues;
import java.io.IOException;
import java.util.Deque;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.felix.scr.annotations.sling.SlingFilter;
import org.apache.felix.scr.annotations.sling.SlingFilterScope;
import org.apache.sling.api.SlingHttpServletRequest;

@SlingFilter(
    order = -1000,
    scope = {SlingFilterScope.REQUEST, SlingFilterScope.FORWARD},
    label = Constants.LABEL_PREFIX + "Request Catcher Filter",
    description = "Filter that stores current Sling HTTP Servlet Request.")
public class RequestCatcherFilter implements Filter, RequestCatcher {

  private final ThreadLocal<Deque<SlingHttpServletRequest>> requests = new ThreadLocal<Deque<SlingHttpServletRequest>>() {
    @Override
    protected Deque<SlingHttpServletRequest> initialValue() {
      return Queues.newArrayDeque();
    }
  };

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    requests.get().clear();
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    final boolean slingRequest = request instanceof SlingHttpServletRequest;

    try {
      if (slingRequest) {
        requests.get().push((SlingHttpServletRequest) request);
      }
      chain.doFilter(request, response);
    } finally {
      if (slingRequest) {
        requests.get().pop();
      }
    }
  }

  @Override
  public Optional<SlingHttpServletRequest> fetchRequest() {
    return Optional.fromNullable(requests.get().peek());
  }

  @Override
  public void destroy() {
    requests.get().clear();
  }
}
