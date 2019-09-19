<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Game">
    <c:if test="${not empty answers}">
        <table>
            <thead>
            <tr>
                <td>

                </td>
                <td>
                    Bull
                </td>
                <td>
                    Cow
                </td>
            </tr>
            <c:forEach var="answer" items="${answers}">
                <tr>
                    <td>
                            ${answer.number}
                    </td>
                    <td>
                            ${answer.bulls}
                    </td>
                    <td>
                            ${answer.cows}
                    </td>
                </tr>
            </c:forEach>
            </thead>
        </table>
    </c:if>
    <c:if test="${empty message}">
        <form method="post">
            <input type="text" name="number" autofocus
                   required oninvalid="this.setCustomValidity('The move consists of 4 non-repeating numbers')"
                   oninput="setCustomValidity('')"
                   pattern=^(?:([0-9])(?!.*\1)){4}$>
            <button>
                Make a move
            </button>
        </form>
    </c:if>

    <c:if test="${not empty message}">
        ${message}<br>
        <form method="get">
            <button>Start a new game</button>
        </form>
    </c:if>
</tags:master>