<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"
%><%@include file="/libs/foundation/global.jsp"
%><%@include file="/libs/wcm/core/components/init/init.jsp"
%><!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="https://code.getmdl.io/1.1.1/material.indigo-pink.min.css">
    <script defer src="https://code.getmdl.io/1.1.1/material.min.js"></script>

    <cq:includeClientLib categories="cq.wcm.edit, slung.author, material"/>

    <title>Slung example</title>
  </head>
  <body>
    <div class="mdl-layout__container">
      <div class="mdl-layout mdl-js-layout">
        <%@include file="./header.jsp" %>
        <%@include file="./drawer.jsp" %>
        <%@include file="./content.jsp" %>
        <%@include file="./footer.jsp" %>
      </div>
    </div>
  </body>
</html>