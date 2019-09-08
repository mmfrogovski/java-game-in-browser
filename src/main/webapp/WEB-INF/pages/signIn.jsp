<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<head>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/singIn.css">
</head>
<tags:master pageTitle="Game">
    <div id="login">
        <form method="post">
            <fieldset>
                <p><span class="fontawesome-user"></span><input type="text" name="login" value="Логин" required></p>
                <p><span class="fontawesome-lock"></span><input type="password" name="password" value="Пароль" required></p>
                <p><input type="submit" value="Войти"/></p>
                <c:if test="${not empty error}">
                    <p class="error">${error}</p><br>
                </c:if>
            </fieldset>
        </form>
        <p>Нет аккаунта? &nbsp;&nbsp;<a href="${pageContext.servletContext.contextPath}/logIn">Регистрация</a><span class="fontawesome-arrow-right"></span></p>
    </div>
</tags:master>