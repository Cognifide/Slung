<%@page contentType="text/html" pageEncoding="utf-8"
%><%@include file="/libs/foundation/global.jsp"
%><%@taglib prefix="slice" uri="http://cognifide.com/jsp/slice"
%><slice:lookup var="model" type="<%=com.cognifide.slung.example.components.material.card.EventCardModel.class%>"/>
<div class="card-event mdl-card mdl-shadow--2dp">
  <div class="mdl-card__title mdl-card--expand">
    <h4>
      ${model.description}<br />
      ${model.date}<br />
      ${model.time}
    </h4>
  </div>
  <div class="mdl-card__actions mdl-card--border">
    <a class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect">${model.label}</a>
    <div class="mdl-layout-spacer"></div>
    <i class="material-icons">${model.icon}</i>
  </div>
</div>
