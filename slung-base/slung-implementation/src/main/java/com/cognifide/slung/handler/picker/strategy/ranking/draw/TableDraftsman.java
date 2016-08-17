package com.cognifide.slung.handler.picker.strategy.ranking.draw;

import com.cognifide.slung.api.handler.Handler;
import com.cognifide.slung.handler.picker.strategy.ranking.table.Board;
import org.apache.commons.lang3.StringUtils;

public class TableDraftsman {

  private static final int MAX_COLUMN_WIDTH = 10;

  private final Board<Handler, Class<?>, Integer> table;

  private final Pen pen;

  public TableDraftsman(Board<Handler, Class<?>, Integer> table) {
    this.table = table;
    this.pen = new Pen();
  }

  public String draw() {
    String drawing = StringUtils.EMPTY;
    if (null != table) {
      Cloth cloth = new Cloth(pen, table.numberOfColumns(), MAX_COLUMN_WIDTH);

      cloth.drawUpperLine().newLine();
      drawHeader(cloth);
      cloth.drawMiddleLine().newLine();
      drawBody(cloth);
      cloth.drawLowerLine().newLine();

      drawing = cloth.toString();
    }
    return drawing;
  }

  private void drawHeader(Cloth cloth) {
    cloth.spearator().colorCell(pen.getShade());
    for (Class<?> column : table.columns()) {
      cloth.spearator().gap().drawClass(column).gap();
    }
    cloth.spearator().newLine();
  }

  private void drawBody(Cloth cloth) {
    for (Handler handler : table.rows()) {
      cloth.spearator().gap().drawClass(handler.getClass());
      for (Class<?> column : table.columns()) {
        Integer cell = table.cell(handler, column);
        cloth.gap().spearator().gap().drawLeft(Integer.toString(cell == null ? 0 : cell));
      }
      cloth.gap().spearator().newLine();
    }
  }
}
