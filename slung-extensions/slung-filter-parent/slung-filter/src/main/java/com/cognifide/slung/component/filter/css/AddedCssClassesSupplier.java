package com.cognifide.slung.component.filter.css;

import com.cognifide.slung.api.configuration.CssClassesConfiguration;
import com.google.common.base.Joiner;
import com.google.common.base.Supplier;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AddedCssClassesSupplier implements Supplier<String> {

  private final CssClassesConfiguration cssClassesConfiguration;

  private final Joiner joiner;

  @Override
  public String get() {
    return joiner.join(cssClassesConfiguration.getAddedCssClasses());
  }

}
