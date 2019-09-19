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
        <a href="${pageContext.servletContext.contextPath}/homePage" class="homeHeader">Home</a>
        <c:choose>
            <c:when test="${user.authorized}">
                <a href="${pageContext.servletContext.contextPath}/createRoom">Create room</a>
                <a href="${pageContext.servletContext.contextPath}/rooms">Room list</a>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.servletContext.contextPath}/signIn">Create room</a>
                <a href="${pageContext.servletContext.contextPath}/signIn">Room list</a>
            </c:otherwise>
        </c:choose>
        <c:choose>
            <c:when test="${user.inRoom!=0}">
                <a href="${pageContext.servletContext.contextPath}/room/${user.inRoom}">Your room</a>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.servletContext.contextPath}">Your room</a>
            </c:otherwise>
        </c:choose>
        <c:choose>
            <c:when test="${user.authorized}">
                <form method="post" class="logOffHeader">
                    <button formaction="${pageContext.servletContext.contextPath}/homePage">
                        <a>Log Off</a>
                    </button>
                </form>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.servletContext.contextPath}/signIn" class="signInHeader">Sign in</a>
            </c:otherwise>
        </c:choose>
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