package com.cognifide.slung.component.filter.css;

import com.cognifide.slung.api.configuration.CssClassesConfiguration;
import com.day.cq.commons.DiffInfo;
import com.day.cq.commons.DiffService;
import com.day.cq.commons.jcr.JcrConstants;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(DiffInfo.class)
public class CssClassesSelectorTest {

  private static final String DIFF_TO = "1.0";

  private static final String NOT_IMPORTANT = "not important";

  private static final List<String> ADDED_CSS_CLASSES = Arrays.asList("add", "add");

  private static final List<String> MODIFIED_CSS_CLASSES = Arrays.asList("modify", "modify");

  private static final List<String> REMOVED_CSS_CLASSES = Arrays.asList("remove", "remove");

  private static final long DATE = 1457733603L;

  private static final Date LAST_MODIFIED_DATE = new Date(DATE);

  private static final Date FUTURE_LAST_MODIFIED_DATE = new Date(DATE + 1);

  @Mock
  private CssClassesConfiguration cssClassesConfiguration;

  @Mock
  private SlingHttpServletRequest request;

  @Mock
  private Resource resource;

  @Mock
  private ValueMap valueMap;

  @Mock
  private Resource oldResource;

  @Mock
  private ValueMap oldValueMap;

  private CssClassesSelector testedObject;

  @Rule
  public MockitoRule mockitoRule = MockitoJUnit.rule();

  @Before
  public void setUp() {
    testedObject = new CssClassesSelector(cssClassesConfiguration);

    when(resource.getValueMap()).thenReturn(valueMap);
    when(oldResource.getValueMap()).thenReturn(oldValueMap);

    setUpCssClassesConfiguration();
  }

  private void setUpCssClassesConfiguration() {
    when(cssClassesConfiguration.getAddedCssClasses()).thenReturn(ADDED_CSS_CLASSES);
    when(cssClassesConfiguration.getModifiedCssClasses()).thenReturn(MODIFIED_CSS_CLASSES);
    when(cssClassesConfiguration.getRemovedCssClasses()).thenReturn(REMOVED_CSS_CLASSES);
  }

  @Test
  public void shouldReturnConcatenatedRemovedCssClassesWhenResourceWasRemoved() {
    //given
    setUpResourceWith(JcrConstants.NT_FROZENNODE);

    //when
    final String actual = testedObject.select(request, resource);

    //then
    verify(resource).getValueMap();
    verify(valueMap).get(JcrConstants.JCR_PRIMARYTYPE, String.class);
    verify(cssClassesConfiguration).getRemovedCssClasses();
    verifyNoMoreInteractions(request, resource, valueMap, cssClassesConfiguration);

    assertThat(actual).isEqualTo("remove remove");
  }

  @Test
  public void shouldReturnConcatenatedAddedCssClassesWhenOldResourceDoesNotExist() {
    //given
    setUpResourceWith(NOT_IMPORTANT);
    setUpRequestWithDiffToWith(null);

    //when
    final String actual = testedObject.select(request, resource);

    //then
    verify(resource).getValueMap();
    verify(valueMap).get(JcrConstants.JCR_PRIMARYTYPE, String.class);
    verify(cssClassesConfiguration).getAddedCssClasses();
    verifyRequestWasAskedAboutDiffTo();
    verifyNoMoreInteractions(request, resource, valueMap, cssClassesConfiguration);

    assertThat(actual).isEqualTo("add add");
  }

  private void setUpResourceWith(final String primaryType) {
    when(valueMap.get(JcrConstants.JCR_PRIMARYTYPE, String.class)).thenReturn(primaryType);
    when(valueMap.get(JcrConstants.JCR_LASTMODIFIED, Date.class)).thenReturn(LAST_MODIFIED_DATE);
  }

  private void setUpRequestWithDiffToWith(Resource oldResource) {
    when(request.getParameter(DiffService.REQUEST_PARAM_DIFF_TO)).thenReturn(DIFF_TO);

    PowerMockito.mockStatic(DiffInfo.class);
    when(DiffInfo.getVersionedResource(resource, DIFF_TO)).thenReturn(oldResource);
  }

  private void verifyRequestWasAskedAboutDiffTo() {
    verify(request).getParameter(DiffService.REQUEST_PARAM_DIFF_TO);

    PowerMockito.verifyStatic();
    DiffInfo.getVersionedResource(resource, DIFF_TO);
  }

  @Test
  public void shouldReturnConcatenatedModifiedCssClassesWhenBothResourceAndOldResourceExistWithDifferentLastModifiedValues() {
    //given
    setUpResourceWith(NOT_IMPORTANT);
    setUpRequestWithDiffToWith(oldResource);
    when(oldValueMap.get(JcrConstants.JCR_LASTMODIFIED, Date.class)).thenReturn(FUTURE_LAST_MODIFIED_DATE);

    //when
    final String actual = testedObject.select(request, resource);

    //then
    verify(resource, times(2)).getValueMap();
    verify(valueMap).get(JcrConstants.JCR_PRIMARYTYPE, String.class);
    verify(valueMap).get(JcrConstants.JCR_LASTMODIFIED, Date.class);
    verify(oldResource).getValueMap();
    verify(oldValueMap).get(JcrConstants.JCR_LASTMODIFIED, Date.class);
    verify(cssClassesConfiguration).getModifiedCssClasses();
    verifyRequestWasAskedAboutDiffTo();
    verifyNoMoreInteractions(request, resource, valueMap, oldResource, oldValueMap, cssClassesConfiguration);

    assertThat(actual).isEqualTo("modify modify");
  }

  @Test
  public void shouldReturnEmptyStringWhenBothResourceAndOldResourceExistWithSameLastModifiedValues() {
    //given
    setUpResourceWith(NOT_IMPORTANT);
    setUpRequestWithDiffToWith(oldResource);
    when(oldValueMap.get(JcrConstants.JCR_LASTMODIFIED, Date.class)).thenReturn(LAST_MODIFIED_DATE);

    //when
    final String actual = testedObject.select(request, resource);

    //then
    verify(resource, times(2)).getValueMap();
    verify(valueMap).get(JcrConstants.JCR_PRIMARYTYPE, String.class);
    verify(valueMap).get(JcrConstants.JCR_LASTMODIFIED, Date.class);
    verify(oldResource).getValueMap();
    verify(oldValueMap).get(JcrConstants.JCR_LASTMODIFIED, Date.class);
    verifyRequestWasAskedAboutDiffTo();
    verifyNoMoreInteractions(request, resource, valueMap, oldResource, oldValueMap, cssClassesConfiguration);

    assertThat(actual).isEmpty();
  }
}
