package com.cognifide.slung.sling.filter;

import com.google.common.base.Optional;
import org.apache.sling.api.SlingHttpServletRequest;

public interface RequestCatcher {

  Optional<SlingHttpServletRequest> fetchRequest();
}
