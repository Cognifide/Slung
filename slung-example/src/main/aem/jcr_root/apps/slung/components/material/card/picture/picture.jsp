<%@page contentType="text/html" pageEncoding="utf-8"
%><%@include file="/libs/foundation/global.jsp"
%><%@taglib prefix="slice" uri="http://cognifide.com/jsp/slice"
%><slice:lookup var="model" type="<%=com.cognifide.slung.example.components.material.card.PictureCardModel.class%>"/>
<div class="card-picture mdl-card mdl-shadow--2dp ${model.imageCss}" style="background: url('${model.image}') center / cover;">
  <div class="mdl-card__title mdl-card--expand"></div>
  <div class="mdl-card__actions">
    <span class="card-picture__filename">${model.label}</span>
  </div>
</div>
