package com.cognifide.slung.handler.picker.strategy.ranking.draw;

import org.apache.commons.lang3.StringUtils;

public class Cloth {

  private static final int NUMBER_OF_ADDITIONAL_COLUMNS_FOR_ROW = 1;

  private final StringBuilder cloth;

  private final Pen pen;

  private final int columns;

  private final int width;

  public Cloth(Pen pen, int columns, int width) {
    this.cloth = new StringBuilder(StringUtils.EMPTY);
    this.pen = pen;
    this.columns = columns;
    this.width = width;
  }

  public int getColumns() {
    return columns;
  }

  public int getWidth() {
    return width;
  }

  public int getNumberOfAdditionalColumnsForRow() {
    return NUMBER_OF_ADDITIONAL_COLUMNS_FOR_ROW;
  }

  public String getGap() {
    return pen.getGap();
  }

  public Cloth draw(String s) {
    cloth.append(s);
    return this;
  }

  public Cloth drawInColumn(String s) {
    cloth.append(StringUtils.abbreviateMiddle(s, pen.getEllipsis(), width));
    return this;
  }

  public Cloth drawLeft(String s) {
    String moved = StringUtils.leftPad(s, width);
    return drawInColumn(moved);
  }

  public Cloth drawRight(String s) {
    String moved = StringUtils.rightPad(s, width);
    return drawInColumn(moved);
  }

  public Cloth drawClass(Class<?> type) {
    cloth.append(new ClassDrawing(type).create(width));
    return this;
  }

  public Cloth newLine() {
    cloth.append(pen.getNewLine());
    return this;
  }

  public Cloth gap() {
    cloth.append(pen.getGap());
    return this;
  }

  public Cloth colorCell(String pattern) {
    cloth.append(StringUtils.repeat(pattern, width + 2 * pen.getGap().length()));
    return this;
  }

  public Cloth spearator() {
    cloth.append(pen.getVerticalLine());
    return this;
  }

  public Cloth drawUpperLine() {
    return new LineDrawing(this)
        .left(pen.getLeftUpperCorner())
        .line(pen.getHorizontalLine())
        .join(pen.getHorizontalUpperJoin())
        .right(pen.getRightUpperCorner())
        .draw();
  }

  public Cloth drawMiddleLine() {
    return new LineDrawing(this)
        .left(pen.getVerticalLeftJoin())
        .line(pen.getHorizontalLine())
        .join(pen.getJoin())
        .right(pen.getVerticalRightJoin())
        .draw();
  }

  public Cloth drawLowerLine() {
    return new LineDrawing(this)
        .left(pen.getLeftLowerCorner())
        .line(pen.getHorizontalLine())
        .join(pen.getHorizontalLowerJoin())
        .right(pen.getRightLowerCorner())
        .draw();
  }

  @Override
  public String toString() {
    return cloth.toString();
  }

}
