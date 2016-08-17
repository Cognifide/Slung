package com.cognifide.slung.handler.text;

import com.cognifide.slung.api.configuration.CssClassesConfiguration;
import com.cognifide.slung.handler.TypeAbstractHandler;
import com.cognifide.slung.handler.text.filter.StringEscapeService;
import com.day.cq.commons.DiffService;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import com.google.common.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import org.apache.commons.lang3.StringUtils;

public abstract class AbstractTextHandler extends TypeAbstractHandler<String> {

  private static final Joiner CSS_CLASSES_JOINER = Joiner.on(StringUtils.SPACE).skipNulls();

  private static final String DEFAULT_TEXT_ADDED_INFIX = "<ins class='textAdded'>";

  private static final String TEXT_ADDED_FORMAT = "<ins class='%s'>";

  private static final String DEFAULT_TEXT_REMOVED_INFIX = "<del class='textRemoved'>";

  private static final String TEXT_REMOVED_FORMAT = "<del class='%s'>";

  private static final Type COLLECTION_OF_STRINGS_TYPE = new TypeToken<Collection<String>>() {
    private static final long serialVersionUID = 1L;
  }.getType();

  @Override
  public Iterable<Type> getAcceptedTypes() {
    return Arrays.<Type>asList(String.class, String[].class, COLLECTION_OF_STRINGS_TYPE);
  }

  @Override
  protected String diff(final Object newValue, final Object oldValue) {
    return unescape(replaceCssClasses(findDiffService().diff(
        escape(newValue),
        escape(oldValue),
        isRichText())));
  }

  private String replaceCssClasses(String s) {
    String result = s;

    CssClassesConfiguration configuration = findCssClassesConfiguration();
    if (!Iterables.isEmpty(configuration.getAddedCssClasses())) {
      String addedCssClasses = String.format(TEXT_ADDED_FORMAT, join(configuration.getAddedCssClasses()));
      result = StringUtils.replace(result, DEFAULT_TEXT_ADDED_INFIX, addedCssClasses);
    }

    if (!Iterables.isEmpty(configuration.getRemovedCssClasses())) {
      String removedCssClasses = String.format(TEXT_REMOVED_FORMAT, join(configuration.getRemovedCssClasses()));
      result = StringUtils.replace(result, DEFAULT_TEXT_REMOVED_INFIX, removedCssClasses);
    }

    return result;
  }

  protected abstract CssClassesConfiguration findCssClassesConfiguration();

  private String join(Iterable<String> cssClasses) {
    return CSS_CLASSES_JOINER.join(cssClasses);
  }

  protected String unescape(String s) {
    return findStringEscapeService().unescape(s);
  }

  protected abstract DiffService findDiffService();

  protected String escape(Object o) {
    return null == o ? null : findStringEscapeService().escape(cast(o));
  }

  protected abstract StringEscapeService findStringEscapeService();

  private String cast(Object o) {
    return String.class.isAssignableFrom(o.getClass()) ? (String) o : o.toString();
  }

  protected boolean isRichText() {
    return false;
  }
}
