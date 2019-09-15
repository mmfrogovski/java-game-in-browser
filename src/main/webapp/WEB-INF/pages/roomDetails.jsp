<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>


<head>
<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/chat.css">
<script src="${pageContext.servletContext.contextPath}/scripts/openSocketScript.js"></script>
</head>
<tags:master pageTitle="Game">
<body>
<h1>Biki i korovi</h1>

<div class="chatbox">

   <div class="messages">
   </div>

   <input class="msg" name="${user.login}">
</div>
</body>


</tags:master>