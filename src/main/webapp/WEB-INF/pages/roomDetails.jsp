<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="users" scope="request" type="java.util.List"/>

<head>
    <link rel="stylesheet" href="styles/details.css">
</head>
<tags:master pageTitle="Game">
    <table>
        <thead>
        <tr>
            <td>
                Users name
            </td>
            <td>
                Room name
            </td>
        </tr>
        </thead>
        <c:forEach var="user" items="${users}">
            <tr>
                <td>
                        ${user.login}
                </td>
                <td>
                        ${room.name}
                </td>
            </tr>
        </c:forEach>
    </table>
    <form method="post">
        <button class="leave" formaction="${pageContext.servletContext.contextPath}/deleteUserFromRoom/${room.id}">Leave from room
        </button>
    </form>
</tags:master>