<%@page contentType="text/html" pageEncoding="utf-8"
%><%@include file="/libs/foundation/global.jsp"
%><%@taglib prefix="slice" uri="http://cognifide.com/jsp/slice"
%><slice:lookup var="model" type="<%=com.cognifide.slung.example.components.material.card.SquareCardModel.class%>"/>
<div class="card-square mdl-card mdl-shadow--2dp">
  <div class="mdl-card__title mdl-card--expand ${model.imageCss}" style="background:url('${model.image}') bottom right 15% no-repeat #46B6AC;">
    <h2 class="mdl-card__title-text">${model.title}</h2>
  </div>
  <div class="mdl-card__supporting-text">${model.description}</div>
  <div class="mdl-card__actions mdl-card--border">
    <a class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect" href="${model.link}">${model.label}</a>
  </div>
</div>
