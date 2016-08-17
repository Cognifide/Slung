package com.cognifide.slung.component.filter;

import com.cognifide.slung.component.filter.configuration.ComponentCssClassesConfiguration;
import com.cognifide.slung.component.filter.configuration.ComponentFilterConfiguration;
import com.cognifide.slung.component.filter.response.ComponentResponse;
import com.day.cq.commons.DiffInfo;
import com.day.cq.commons.DiffService;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.wcm.api.WCMMode;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
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
public class DiffComponentFilterTest {

  private static final String RESOURCE_TYPE = "resource/type";

  private static final String RESOURCE_PATH = "/path/resource";

  private static final String RESOURCE_PRIMARY_TYPE = "primary/type";

  private static final String WCM_MODE_PARAMETER = WCMMode.class.getName();

  private static final String REQUEST_PATH = "/path/";

  private static final String DIFF_TO = "1.0";

  private static final String CHARACTER_ENCODING = "utf8";

  @Mock
  private ComponentFilterConfiguration componentFilterConfiguration;

  @Mock
  private ComponentCssClassesConfiguration cssClassesConfiguration;

  @Mock
  private FilterConfig filterConfig;

  @Mock
  private SlingHttpServletRequest request;

  @Mock
  private Resource resource;

  @Mock
  private Resource oldResource;

  @Mock
  private HttpServletResponse response;

  @Mock
  private PrintWriter writer;

  @Mock
  private FilterChain chain;

  @InjectMocks
  public DiffComponentFilter testedObject = new DiffComponentFilter();

  @Rule
  public MockitoRule rule = MockitoJUnit.rule();

  @Before
  public void setUp() throws ServletException {
    testedObject.init(filterConfig);
  }

  @Test
  public void shouldApplyFilterToRequest() throws IOException, ServletException {
    //given
    setUpConfiguration();
    setUpRequest();
    setUpResource();
    setUpResponse();
    setUpOldResource();

    //when
    testedObject.doFilter(request, response, chain);

    //then
    verifyConfiguration();
    verifyRequestWasAskedForDiffMode();
    verifyResourceWasAskedForDiffMode();
    verify(oldResource).getValueMap();
    verify(response, times(2)).getCharacterEncoding();
    verify(response).getWriter();
    verify(writer).write("");
    verify(writer).flush();
    verify(chain).doFilter(eq(request), isA(ComponentResponse.class));

    verifyNoMoreInteractions(resource, oldResource, response, writer);
  }

  private void setUpConfiguration() {
    when(componentFilterConfiguration.isEnabled()).thenReturn(true);
    when(componentFilterConfiguration.canFilter(RESOURCE_TYPE)).thenReturn(true);
  }

  private void setUpRequest() {
    when(request.getAttribute(WCM_MODE_PARAMETER)).thenReturn(WCMMode.PREVIEW);
    when(request.getParameter(DiffService.REQUEST_PARAM_DIFF_TO)).thenReturn(DIFF_TO);
    RequestPathInfo requestPathInfo = mock(RequestPathInfo.class);
    when(request.getRequestPathInfo()).thenReturn(requestPathInfo);
    when(requestPathInfo.getResourcePath()).thenReturn(REQUEST_PATH);
  }

  private void setUpResource() {
    when(request.getResource()).thenReturn(resource);
    when(resource.getResourceType()).thenReturn(RESOURCE_TYPE);
    when(resource.getPath()).thenReturn(RESOURCE_PATH);
    ValueMap valueMap = mock(ValueMap.class);
    when(resource.getValueMap()).thenReturn(valueMap);
    when(valueMap.get(JcrConstants.JCR_PRIMARYTYPE, String.class)).thenReturn(RESOURCE_PRIMARY_TYPE);
  }

  private void setUpResponse() throws IOException {
    when(response.getCharacterEncoding()).thenReturn(CHARACTER_ENCODING);
    when(response.getWriter()).thenReturn(writer);
  }

  private void setUpOldResource() {
    PowerMockito.mockStatic(DiffInfo.class);
    when(DiffInfo.getVersionedResource(resource, DIFF_TO)).thenReturn(oldResource);
    ValueMap valueMap = mock(ValueMap.class);
    when(oldResource.getValueMap()).thenReturn(valueMap);
  }

  private void verifyConfiguration() {
    verify(componentFilterConfiguration).isEnabled();
    verify(componentFilterConfiguration).canFilter(RESOURCE_TYPE);
    verifyNoMoreInteractions(componentFilterConfiguration, cssClassesConfiguration, filterConfig);
  }

  private void verifyRequestWasAskedForDiffMode() {
    verify(request, times(2)).getResource();
    verify(request).getAttribute(WCM_MODE_PARAMETER);
    verify(request, times(2)).getParameter(DiffService.REQUEST_PARAM_DIFF_TO);
    verify(request).getRequestPathInfo();
    verifyNoMoreInteractions(request);
  }

  private void verifyResourceWasAskedForDiffMode() {
    verify(resource).getResourceType();
    verify(resource).getPath();
    verify(resource, times(2)).getValueMap();
    PowerMockito.verifyStatic();
    DiffInfo.getVersionedResource(resource, DIFF_TO);
    verifyNoMoreInteractions(resource);
  }
}
