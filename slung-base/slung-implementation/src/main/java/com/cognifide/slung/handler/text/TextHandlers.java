package com.cognifide.slung.handler.text;

public class TextHandlers {

  public static final int TEXT_HANDLER_RANKING = 400;

  public static final String TEXT_HANDLER_NAME = "text";

  public static final int RICH_TEXT_HANDLER_RANKING = 300;

  public static final String RICH_TEXT_HANDLER_NAME = "richtext";

  public static final String RICH_TEXT_RESOURCE_TYPE = RICH_TEXT_HANDLER_NAME;

  public static final String RICH_TEXT_PROPERTY_NAME = RICH_TEXT_HANDLER_NAME;

  public static final String RICH_TEXT_X_TYPE = RICH_TEXT_HANDLER_NAME;

  public static final int UNESCAPED_TEXT_HANDLER_RANKING = 200;

  public static final String UNESCAPED_TEXT_HANDLER_NAME = "unescaped";

  public static final String UNESCPAED_TEXT_RESOURCE_TYPE = UNESCAPED_TEXT_HANDLER_NAME;

  public static final String UNESCPAED_TEXT_PROPERTY_NAME = UNESCAPED_TEXT_HANDLER_NAME;

  private TextHandlers() {
    throw new AssertionError();
  }
}
