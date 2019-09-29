<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="users" scope="request" type="java.util.List"/>

<head>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/chat.css">
    <script src="${pageContext.servletContext.contextPath}/scripts/chat.js"></script>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/details.css">
</head>
<tags:master pageTitle="Game">

    <table id="tbl">
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
                <td id="${user.login}">
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
    <div class="chatbox">

        <div class="messages" id="${user.inRoom}">
        </div>

        <input class="msg" name="${user.login}">
    </div>
    <c:choose>
        <c:when test="${room.firstUserId !=0 && room.secondUserId != 0}">
            <button class="ready" id="${room.name}">Start</button>
        </c:when>
        <c:otherwise>
            <button class="ready" id="${room.name}" disabled>Start</button>
        </c:otherwise>
    </c:choose>
</tags:master>
