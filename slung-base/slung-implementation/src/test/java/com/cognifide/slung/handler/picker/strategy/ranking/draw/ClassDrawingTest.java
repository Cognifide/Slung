package com.cognifide.slung.handler.picker.strategy.ranking.draw;

import com.cognifide.slung.handler.picker.strategy.ranking.draw.ClassDrawing;
import com.cognifide.slung.handler.picker.strategy.ranking.resolver.resourcetype.ResourceTypeRankResolverProvider;
import java.util.Arrays;
import java.util.Collection;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ClassDrawingTest {

  private static final int SHORT_MAXIMUM_WIDTH = 5;

  private static final int MEDIUM_MAXIMUM_WIDTH = 10;

  private static final int LONG_MAXIMUM_WIDTH = 20;

  @Parameters
  public static Collection<Object[]> parameters() {
    return Arrays.asList(new Object[][]{
      {String.class, SHORT_MAXIMUM_WIDTH, "St..."},
      {String.class, MEDIUM_MAXIMUM_WIDTH, "  String  "},
      {ResourceTypeRankResolverProvider.class, MEDIUM_MAXIMUM_WIDTH, "Resourc..."},
      {ResourceTypeRankResolverProvider.class, LONG_MAXIMUM_WIDTH, "ResourceTypeRankR..."}
    });
  }

  @Parameter
  public Class<?> type;

  @Parameter(value = 1)
  public int maxWidth;

  @Parameter(value = 2)
  public String expected;

  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void shouldThrowNullPointerExceptionWhenClassIsNull() {
    //given
    exception.expect(NullPointerException.class);
    exception.expectMessage("Cannot create drawing of null class.");

    //when
    ClassDrawing testedObject = new ClassDrawing(null);
  }

  @Test
  public void shouldCreateDrawingAccordingToMaximumWidth() {
    //given
    ClassDrawing testedObject = new ClassDrawing(type);
    //when
    String actual = testedObject.create(maxWidth);
    //then
    assertThat(actual).isEqualTo(expected);
  }
}
