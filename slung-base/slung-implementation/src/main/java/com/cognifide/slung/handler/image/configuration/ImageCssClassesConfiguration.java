package com.cognifide.slung.handler.image.configuration;

import com.cognifide.slung.api.Constants;
import com.cognifide.slung.api.configuration.AbstractCssClassesConfiguration;
import com.cognifide.slung.api.configuration.CssClassesProperties;
import java.util.Map;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Modified;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.PropertyUnbounded;
import org.apache.felix.scr.annotations.Service;

@Service(value = ImageCssClassesConfiguration.class)
@Component(
    label = Constants.LABEL_PREFIX + "Diff image css classes configuration",
    description = "Css classes defined here will be added depending on image comparison",
    metatype = true)
@Properties({
  @Property(
      name = CssClassesProperties.ADDED_CSS_CLASSES_PROPERTY,
      value = "diff-image-added",
      label = CssClassesProperties.ADDED_CSS_CLASSES_LABEL,
      description = CssClassesProperties.ADDED_CSS_CLASSES_DESCRIPTION,
      unbounded = PropertyUnbounded.ARRAY),
  @Property(
      name = CssClassesProperties.MODIFIED_CSS_CLASSES_PROPERTY,
      value = "diff-image-modified",
      label = CssClassesProperties.MODIFIED_CSS_CLASSES_LABEL,
      description = CssClassesProperties.MODIFIED_CSS_CLASSES_DESCRIPTION,
      unbounded = PropertyUnbounded.ARRAY),
  @Property(
      name = CssClassesProperties.REMOVED_CSS_CLASSES_PROPERTY,
      value = "diff-image-removed",
      label = CssClassesProperties.REMOVED_CSS_CLASSES_LABEL,
      description = CssClassesProperties.REMOVED_CSS_CLASSES_DESCRIPTION,
      unbounded = PropertyUnbounded.ARRAY)})
public class ImageCssClassesConfiguration extends AbstractCssClassesConfiguration {

  @Activate
  @Modified
  @Override
  protected void activate(final Map<String, Object> configuration) {
    super.activate(configuration);
  }

}
