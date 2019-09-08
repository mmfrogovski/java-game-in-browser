<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="pageTitle" required="true" %>

<html>
<head>
    <title>${pageTitle}</title>
    <link href='http://fonts.googleapis.com/css?family=Lobster+Two' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/main.css">
    <meta charset="UTF-8"/>
</head>
<body>
<header>
    <a href="${pageContext.servletContext.contextPath}/signIn">
        <img src="${pageContext.servletContext.contextPath}/images/logo.jpg">
        DURAK
    </a>
</header>
<main>
    <jsp:doBody/>
</main>
<footer>
    <p>(c) Create Game 2019</p>
</footer>
</body>
</html>