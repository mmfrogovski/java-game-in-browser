<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="users" scope="request" type="java.util.List"/>


<head>
    <script src="${pageContext.servletContext.contextPath}/scripts/multiplayer.js"></script>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/multiplay.css">
</head>
<tags:master pageTitle="Game">
    <div class="userScore">
        <div class="firstUserScore" id="${users[0].login}">
            <div>${users[0].login}</div>
            <div class="score1">

            </div>
        </div>
        <div class="secondUserScore" id="${users[1].login}">
            <div>${users[1].login}</div>
            <div class="score2">

            </div>
        </div>
    </div>
    <div class="enterUserNumber">
        <div>
            <h3>Enter your number</h3>
            <form>
                <input type="text" name="userNumber">
                <button class="enter">
                    Enter
                </button>
            </form>
        </div>
    </div>
    <div class="sendBox" id="${user.login}">
        <form>
            <input type="text" name="number">
            <button class="move">
                Make a move
            </button>
        </form>
    </div>
    <div class="newGame">
        <button>Start a new game</button>
    </div>
</tags:master>