<%@page contentType="text/html" pageEncoding="utf-8"
%><%@include file="/libs/foundation/global.jsp"
%><%@taglib prefix="slice" uri="http://cognifide.com/jsp/slice"
%><slice:lookup var="model" type="<%=com.cognifide.slung.example.model.SampleModel.class%>"/>
<h3>Sample component</h3>
<ul class='flex-container'>
  <li class='flex-item'><span class='property-name'>Text:</span> ${model.text}</li>
  <li class='flex-item'><span class='property-name'>Nested text:</span> ${model.nestedText}</li>
</ul>
