<%@page contentType="text/html" pageEncoding="utf-8"
%><%@taglib prefix="sling" uri="http://sling.apache.org/taglibs/sling"
%><%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><sling:defineObjects /><sling:adaptTo adaptable="${resource}" adaptTo="com.cognifide.slung.example.model.SlingExampleModel" var="model"/>
<h3>[Slung] [Sling] [Jsp] component</h3>
<ul class='flex-container'>
  <li class='flex-item'><span class='property-name'>Text:</span> ${model.text}</li>
  <li class='flex-item'><span class='property-name'>Red text:</span> ${model.redText}</li>
  <li class='flex-item'><span class='property-name'>Multiple texts:</span>
    <ul>
      <c:forEach var="element" items="${model.elements}">
        <li>${element}</li>
        </c:forEach>
    </ul>
  </li>
  <li class='flex-item'><span class='property-name'>Multiple texts (list):</span>
    <ul>
      <c:forEach var="element" items="${model.elementsAsList}">
        <li>${element}</li>
        </c:forEach>
    </ul>
  </li>
  <li class='flex-item'><span class='property-name'>Nested text:</span> ${model.nestedText}</li>
  <li class='flex-item'><span class='property-name'>Rich text:</span> ${model.richText}</li>
  <li class='flex-item'><span class='property-name'>Pin:</span> ${model.pin}</li>
  <li class='flex-item'><span class='property-name'>Number:</span> ${model.number}</li>
  <li class='flex-item'><span class='property-name'>Number as string:</span> ${model.numberAsString}</li>
  <li class='flex-item'><span class='property-name'>Condition:</span> ${model.condition}</li>
  <li class='flex-item'><span class='property-name'>Condition as string:</span> ${model.conditionAsString}</li>
  <li class='flex-item'><span class='property-name'>Image:</span><img class="${model.cssClasses}" src="${model.imagePath}" /></li>
  <li class='flex-item'><span class='property-name'>Image 2:</span><img class="${model.cssClasses2}" src="${model.imagePath2}" /></li>
  <li class='flex-item'><span class='property-name'>Multiple texts (list):</span>
    <ul>
      <c:forEach var="element" items="${model.links}">
        <li>${element.title} = ${element.url}</li>
        </c:forEach>
    </ul>
  </li>
</ul>