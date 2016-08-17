package com.cognifide.slung.handler.text.filter;

import com.adobe.granite.xss.XSSAPI;
import com.cognifide.slung.api.Constants;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;

@Service
@Component(
    label = Constants.LABEL_PREFIX + "Html filter service",
    description = "Removes dangerous html from strings.")
public class HtmlEscapeService implements StringEscapeService {

  @Reference
  private XSSAPI xssApi;

  @Override
  public String escape(String s) {
    return xssApi.filterHTML(s);
  }

  @Override
  public String unescape(String s) {
    return StringEscapeUtils.unescapeHtml4(s);
  }

}
