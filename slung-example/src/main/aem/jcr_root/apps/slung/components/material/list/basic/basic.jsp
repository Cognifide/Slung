<%@page contentType="text/html" pageEncoding="utf-8"
%><%@include file="/libs/foundation/global.jsp"
%><%@taglib prefix="slice" uri="http://cognifide.com/jsp/slice"
%><slice:lookup var="model" type="<%=com.cognifide.slung.example.components.material.list.BasicListModel.class%>"/>
<h5>${model.topic}</h5>
<ul class="demo-list-item mdl-list">
  <c:forEach var="item" items="${model.items}">
    <li class="mdl-list__item">
      <span class="mdl-list__item-primary-content">${item}</span>
    </li>
  </c:forEach>
</ul>
