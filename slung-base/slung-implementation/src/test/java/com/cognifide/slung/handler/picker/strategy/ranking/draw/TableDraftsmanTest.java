package com.cognifide.slung.handler.picker.strategy.ranking.draw;

import org.junit.Ignore;

@Ignore
public class TableDraftsmanTest {
//
//  private static final List<Handler> HANDLERS = Arrays.<Handler>asList(
//      new TextHandler(),
//      new RichTextHandler(),
//      new UnescapedTextHandler(),
//      new ImageHandler());
//
//  @SuppressWarnings("unchecked")
//  private static final List<Class<? extends RankResolverProvider>> RANK_RESOLVER_PROVIDERS = Arrays.asList(
//      NameRankResolverProvider.class,
//      PropertyNameRankResolverProvider.class,
//      ResourceTypeRankResolverProvider.class,
//      ValueRankResolverProvider.class,
//      XTypeRankResolverProvider.class);
//
//  private static final int POINTS = 50;
//
//  private static final String EXPECTED_RANKING = ""
//      + "┌────────────┬────────────┬────────────┬────────────┬────────────┬────────────┐\n"
//      + "│░░░░░░░░░░░░│ NameRan... │ Propert... │ Resourc... │ ValueRa... │ XTypeRa... │\n"
//      + "├────────────┼────────────┼────────────┼────────────┼────────────┼────────────┤\n"
//      + "│ TextHan... │         50 │         50 │         50 │         50 │         50 │\n"
//      + "│ RichTex... │         50 │         50 │         50 │         50 │         50 │\n"
//      + "│ Unescap... │         50 │         50 │         50 │         50 │         50 │\n"
//      + "│ ImageHa... │         50 │         50 │         50 │         50 │         50 │\n"
//      + "└────────────┴────────────┴────────────┴────────────┴────────────┴────────────┘\n";
//
//  @Test
//
//  public void shouldDrawEmptyDrawingWhenRankingIsNull() {
//    //given
//    TableDraftsman testedObject = new TableDraftsman(null);
//    //when
//    String actual = testedObject.draw();
//    //then
//    assertThat(actual).isEmpty();
//  }
//
//  @Test
//  public void shouldDrawDrawingWithGivenRanking() {
//    //given
//    TableDraftsman testedObject = new TableDraftsman(createTable());
//    //when
//    String actual = testedObject.draw();
//    //then
////    assertThat(actual).isEqualTo(EXPECTED_RANKING);
//  }
//
//  @SuppressWarnings("unchecked")
//  private Table<Handler, Class<? extends RankResolverProvider>, Integer> createTable() {
//    Table<Handler, Class<? extends RankResolverProvider>, Integer> table = mock(Table.class);
//    when(table.numberOfRows()).thenReturn(HANDLERS.size());
//    when(table.rows()).thenReturn(HANDLERS);
//    when(table.numberOfColumns()).thenReturn(RANK_RESOLVER_PROVIDERS.size());
//    when(table.columns()).thenReturn(RANK_RESOLVER_PROVIDERS);
//    when(table.cell(any(Handler.class), (Class<? extends RankResolverProvider>) any(Class.class))).thenReturn(POINTS);
//
//    return table;
//  }
}
