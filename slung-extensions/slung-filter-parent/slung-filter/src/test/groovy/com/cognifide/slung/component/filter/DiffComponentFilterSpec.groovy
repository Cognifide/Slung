package com.cognifide.slung.component.filter

import com.cognifide.slung.component.filter.configuration.ComponentCssClassesConfiguration
import com.cognifide.slung.component.filter.configuration.ComponentFilterConfiguration
import com.cognifide.slung.component.filter.response.ComponentResponse
import com.day.cq.commons.DiffService
import com.day.cq.wcm.api.WCMMode
import javax.servlet.FilterChain
import javax.servlet.FilterConfig
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletResponse
import org.apache.jackrabbit.JcrConstants;
import org.apache.sling.api.SlingHttpServletRequest
import org.apache.sling.api.resource.Resource
import org.apache.sling.api.resource.ValueMap
import org.apache.sling.api.request.RequestPathInfo
import org.mockito.internal.util.reflection.Whitebox
import spock.lang.Specification
import spock.lang.Unroll

class DiffComponentFilterSpec extends Specification {

  static final RESOURCE_TYPE = 'resource/type'

  static final NOT_MATCHING_RESOURCE_PATH = '/resource/path'

  static final RESOURCE_PATH = '/path/resource'

  static final WCM_MODE_PARAMETER = WCMMode.name

  static final DIFF_TO = '1.0'

  static final REQUEST_PATH = '/path/'

  ComponentFilterConfiguration configuration = Mock()

  ComponentCssClassesConfiguration cssClassesConfiguration = Mock()
  
  FilterConfig filterConfig = Mock()

  ServletRequest request = Mock()

  SlingHttpServletRequest sRequest = Mock()

  RequestPathInfo requestPathInfo = Mock()

  Resource resource = Mock()

  ValueMap valueMap = Mock()

  ServletResponse response = Mock()

  HttpServletResponse hResponse = Mock()

  PrintWriter writer = Mock()

  FilterChain chain = Mock()

  DiffComponentFilter testedObject = new DiffComponentFilter()

  def 'when filter is disabled then execution should be passed down'() {
    given: 'filter is created with disabled configuration'
      setUpFilter()

    when: 'filter is used'
      testedObject.doFilter(request, response, chain)

    then: 'execution is passed down'
      1 * configuration.enabled >> false
      1 * chain.doFilter(request, response)
      0 * _
  }

  def setUpFilter() {
    Whitebox.setInternalState(testedObject, 'componentFilterConfiguration', configuration)
    Whitebox.setInternalState(testedObject, 'cssClassesConfiguration', cssClassesConfiguration)
    testedObject.init(filterConfig)
  }

  def 'when request is not sling http servlet request instance then execution should be passed down'() {
    given: 'filter is created with enabled configuration'
      setUpFilter()

    when: 'filter is used'
      testedObject.doFilter(request, response, chain)

    then: 'execution is passed down'
      1 * configuration.enabled >> true
      1 * chain.doFilter(request, response)
      0 * _
  }

  def 'when response is not http servlet response instance then execution should be passed down' () {
    given: 'filter is created with enabled configuration'
      setUpFilter()

    when: 'filter is used'
      testedObject.doFilter(sRequest, response, chain)

    then: 'execution is passed down'
      1 * configuration.enabled >> true
      1 * chain.doFilter(sRequest, response)
      0 * _
  }

  def 'when resource type is not present in configuration then execution should be passed down' () {
    given: 'filter is created with enabled configuration'
      setUpFilter()

    when: 'filter is used'
      testedObject.doFilter(sRequest, hResponse, chain)

    then: 'configuration is checked'
      with (configuration) {
        1 * isEnabled() >> true
        1 * canFilter(RESOURCE_TYPE) >> false
      }
    and: 'request resource is asked for type'
      1 * sRequest.resource >> resource
      1 * resource.resourceType >> RESOURCE_TYPE
    and: 'execution is passed down'
      1 * chain.doFilter(sRequest, hResponse)
      0 * _
  }

  @Unroll
  def 'when request is in #mode mode then execution should be passed down' () {
    given: 'filter is created with enabled configuration'
      setUpFilter()

    when: 'filter is used'
      testedObject.doFilter(sRequest, hResponse, chain)

    then: 'configuration is checked'
      interaction {
        setUpFilterConfiguration()
      }
    and: 'request resource is asked for type'
      1 * sRequest.resource >> resource
      1 * resource.resourceType >> RESOURCE_TYPE
    and: 'request is asked for wcm mode'
      1 * sRequest.getAttribute(WCM_MODE_PARAMETER) >> mode
    and: 'execution is passed down'
      1 * chain.doFilter(sRequest, hResponse)
      0 * _

    where:
      mode << [null, WCMMode.DISABLED, WCMMode.EDIT, WCMMode.ANALYTICS, WCMMode.READ_ONLY, WCMMode.DESIGN]
  }

  def setUpFilterConfiguration() {
     with (configuration) {
        1 * isEnabled() >> true
        1 * canFilter(RESOURCE_TYPE) >> true
      }
  }

  def 'when request does not have "cq_diffTo" attribute then execution should be passed down' () {
    given: 'filter is created with enabled configuration'
      setUpFilter()

    when: 'filter is used'
      testedObject.doFilter(sRequest, hResponse, chain)

    then: 'configuration is checked'
      interaction {
        setUpFilterConfiguration()
      }
    and: 'request resource is asked for type'
      1 * sRequest.resource >> resource
      1 * resource.resourceType >> RESOURCE_TYPE
    and: 'request is asked for "cq_diffTo" parameter'
      1 * sRequest.getAttribute(WCM_MODE_PARAMETER) >> WCMMode.PREVIEW
      1 * sRequest.getParameter(DiffService.REQUEST_PARAM_DIFF_TO)
    and: 'execution is passed down'
      1 * chain.doFilter(sRequest, hResponse)
      0 * _
  }

  def 'when request resource path does not start with request path then execution should be passed down' () {
    given: 'filter is created with enabled configuration'
      setUpFilter()

    when: 'filter is used'
      testedObject.doFilter(sRequest, hResponse, chain)

    then: 'configuration is checked'
      interaction {
        setUpFilterConfiguration()
      }
    and: 'request resource is asked for path'
      2 * sRequest.resource >> resource
      with (resource) {
        1 * getResourceType() >> RESOURCE_TYPE
        1 * getPath() >> NOT_MATCHING_RESOURCE_PATH
      }
    and: 'request is asked for path'
      interaction {
        setUpSlingHttpServletRequest()
      }
    and: 'execution is passed down'
      1 * chain.doFilter(sRequest, hResponse)
      0 * _
  }
  
  def setUpSlingHttpServletRequest() {
    1 * sRequest.getAttribute(WCM_MODE_PARAMETER) >> WCMMode.PREVIEW
    1 * sRequest.getParameter(DiffService.REQUEST_PARAM_DIFF_TO) >> DIFF_TO
    1 * sRequest.requestPathInfo >> requestPathInfo
    1 * requestPathInfo.resourcePath >> REQUEST_PATH
  }

  def 'when in diff mode and resource was removed, removed css classes should be added' () {
    given: 'filter is created with enabled configuration'
      setUpFilter()

    when: 'filter is used'
      testedObject.doFilter(sRequest, hResponse, chain)

    then: 'configuration is checked'
      interaction {
        setUpFilterConfiguration()
      }
    and: 'request resource is asked for type'
      interaction {
        setUpResource()
      }
      1 * valueMap.get(JcrConstants.JCR_PRIMARYTYPE, String.class) >> JcrConstants.NT_FROZENNODE
    and: 'request is asked for path'
      interaction {
        setUpSlingHttpServletRequest()
      }
    and: 'response is asked for enconding'
      2 * hResponse.characterEncoding >> 'utf8'
    and: 'response output is wrapped with removed css classes'
      1 * hResponse.writer >> writer
      with (writer) {
        1 * write('<span class="');
        1 * write('remove remove');
        1 * write('')
        1 * write('">');
        1 * write('</span>');
        1 * flush()
      }
      1 * cssClassesConfiguration.removedCssClasses >> ['remove', 'remove']
    and: 'execution is passed down'
      1 * chain.doFilter(sRequest, _ as ComponentResponse)
      0 * _
  }

  def setUpResource() {
    2 * sRequest.resource >> resource
    1 * resource.resourceType >> RESOURCE_TYPE
    1 * resource.path >> RESOURCE_PATH
    1 * resource.valueMap >> valueMap
  }
}
