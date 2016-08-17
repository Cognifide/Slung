package com.cognifide.slung.handler.picker.strategy.ranking.draw;

import org.apache.commons.lang3.StringUtils;

public class Pen {

  private static final String BOX_DRAWINGS_LIGHT_HORIZONTAL = "─";

  private static final String BOX_DRAWINGS_LIGHT_VERTICAL = "│";

  private static final String BOX_DRAWINGS_LIGHT_DOWN_AND_RIGHT = "┌";

  private static final String BOX_DRAWINGS_LIGHT_DOWN_AND_LEFT = "┐";

  private static final String BOX_DRAWINGS_LIGHT_UP_AND_RIGHT = "└";

  private static final String BOX_DRAWINGS_LIGHT_UP_AND_LEFT = "┘";

  private static final String BOX_DRAWINGS_LIGHT_VERTICAL_AND_RIGHT = "├";

  private static final String BOX_DRAWINGS_LIGHT_VERTICAL_AND_LEFT = "┤";

  private static final String BOX_DRAWINGS_LIGHT_DOWN_AND_HORIZONTAL = "┬";

  private static final String BOX_DRAWINGS_LIGHT_UP_AND_HORIZONTAL = "┴";

  private static final String BOX_DRAWINGS_LIGHT_VERTICAL_AND_HORIZONTAL = "┼";

  private static final String LIGHT_SHADE = "░";

  private static final String NEW_LINE = System.lineSeparator();

  private static final String GAP = StringUtils.SPACE;

  private static final String ELLIPSIS = "...";

  public String getHorizontalLine() {
    return BOX_DRAWINGS_LIGHT_HORIZONTAL;
  }

  public String getVerticalLine() {
    return BOX_DRAWINGS_LIGHT_VERTICAL;
  }

  public String getLeftUpperCorner() {
    return BOX_DRAWINGS_LIGHT_DOWN_AND_RIGHT;
  }

  public String getRightUpperCorner() {
    return BOX_DRAWINGS_LIGHT_DOWN_AND_LEFT;
  }

  public String getLeftLowerCorner() {
    return BOX_DRAWINGS_LIGHT_UP_AND_RIGHT;
  }

  public String getRightLowerCorner() {
    return BOX_DRAWINGS_LIGHT_UP_AND_LEFT;
  }

  public String getVerticalLeftJoin() {
    return BOX_DRAWINGS_LIGHT_VERTICAL_AND_RIGHT;
  }

  public String getVerticalRightJoin() {
    return BOX_DRAWINGS_LIGHT_VERTICAL_AND_LEFT;
  }

  public String getHorizontalUpperJoin() {
    return BOX_DRAWINGS_LIGHT_DOWN_AND_HORIZONTAL;
  }

  public String getHorizontalLowerJoin() {
    return BOX_DRAWINGS_LIGHT_UP_AND_HORIZONTAL;
  }

  public String getJoin() {
    return BOX_DRAWINGS_LIGHT_VERTICAL_AND_HORIZONTAL;
  }

  public String getShade() {
    return LIGHT_SHADE;
  }

  public String getNewLine() {
    return NEW_LINE;
  }

  public String getGap() {
    return GAP;
  }

  public String getEllipsis() {
    return ELLIPSIS;
  }
}
