package com.cognifide.slung.handler.picker.strategy.ranking.draw;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;

public class LineDrawing {

  private final Cloth cloth;

  private String left;

  private String line;

  private String join;

  private String right;

  public LineDrawing(Cloth cloth) {
    this.cloth = Preconditions.checkNotNull(cloth);
  }

  public LineDrawing left(String left) {
    this.left = left;
    return this;
  }

  public LineDrawing line(String line) {
    this.line = line;
    return this;
  }

  public LineDrawing join(String join) {
    this.join = join;
    return this;
  }

  public LineDrawing right(String right) {
    this.right = right;
    return this;
  }

  public Cloth draw() {
    Preconditions.checkNotNull(left);
    Preconditions.checkNotNull(line);
    Preconditions.checkNotNull(join);
    Preconditions.checkNotNull(right);
    cloth.draw(left);
    for (int i = 0; i < cloth.getColumns() + cloth.getNumberOfAdditionalColumnsForRow(); i++) {
      cloth.draw(StringUtils.repeat(line, computeLineLengthBetweenJoins(i)));
      if (!isLast(i)) {
        cloth.draw(join);
      }
    }
    cloth.draw(right);
    return cloth;
  }

  private int computeLineLengthBetweenJoins(int i) {
    return cloth.getWidth() + 2 * cloth.getGap().length();
  }

  private boolean isLast(int i) {
    return i == cloth.getColumns() - 1 + cloth.getNumberOfAdditionalColumnsForRow();
  }
}
