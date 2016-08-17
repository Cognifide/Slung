<%@page contentType="text/html" pageEncoding="utf-8"
%><%@include file="/libs/foundation/global.jsp"
%><%@taglib prefix="slice" uri="http://cognifide.com/jsp/slice"
%><slice:lookup var="model" type="<%=com.cognifide.slung.example.model.SliceExampleModel.class%>"/>
<h3>[Slung] [Slice] [Jsp] component</h3>
<ul class='flex-container'>
  <li class='flex-item'><span class='property-name'>Red text:</span> ${model.redText}</li>
  <li class='flex-item'><span class='property-name'>Multiple texts (list):</span>
    <ul>
      <c:forEach var="element" items="${model.elementsAsList}">
        <li>${element}</li>
      </c:forEach>
    </ul>
  </li>
  <li class='flex-item'><span class='property-name'>Nested text:</span> ${model.nestedText}</li>
  <li class='flex-item'><span class='property-name'>Pin:</span> ${model.pin}</li>
  <li class='flex-item'><span class='property-name'>Number:</span> ${model.number}</li>
  <li class='flex-item'><span class='property-name'>Number as string:</span> ${model.numberAsString}</li>
  <li class='flex-item'><span class='property-name'>Condition:</span> ${model.condition}</li>
  <li class='flex-item'><span class='property-name'>Condition as string:</span> ${model.conditionAsString}</li>
  <li class='flex-item'><span class='property-name'>Multiple texts (list):</span>
    <ul>
      <c:forEach var="element" items="${model.links}">
        <li>${element.title} = ${element.url}</li>
      </c:forEach>
    </ul>
  </li>
</ul>
