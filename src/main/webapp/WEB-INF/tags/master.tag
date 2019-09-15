<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="pageTitle" required="true" %>

<html>
<head>
    <title>${pageTitle}</title>
    <link href='http://fonts.googleapis.com/css?family=Lobster+Two' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/style.css">
    <meta charset="UTF-8"/>
</head>
<body>
<header>

    <div class="container blue circleBehind">
        <a href="${pageContext.servletContext.contextPath}/homePage" style="right: 30%;">HOME</a>
        <c:if test="${user.authorized}">
            <a href="${pageContext.servletContext.contextPath}/createRoom">CREATE ROOM</a>
            <a href="${pageContext.servletContext.contextPath}/rooms">ROOM LIST</a>
        </c:if>
        <c:if test="${!user.authorized}">
            <a href="${pageContext.servletContext.contextPath}/signIn">Sign in</a>
        </c:if>
        <c:if test="${user.authorized}">
            <jsp:include page="/logOff"/>
        </c:if>
    </div>
</header>
<main>
    <jsp:doBody/>
</main>
<footer>
    <div>All rights Reserved</div>
</footer>
</body>
</html>