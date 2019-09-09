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
                <p>
                    <span class="fontawesome-user"></span>
                    <input type="text" id="log" name="login"
                           required oninvalid="this.setCustomValidity('Incorrect login.')"
                           oninput="setCustomValidity('')"
                           placeholder="Login" pattern="[A-Za-z0-9?=.\d]{1,15}"
                           title="Please enter login."
                    />
                </p>
                <p>
                    <span class="fontawesome-lock"></span>
                    <input type="password" id="password" name="password1"
                           required oninvalid="this.setCustomValidity('At least 8 characters long (and less than 100 characters)\n'+
                            'Contains at least 1 number\n'+
                            'Contains at least 1 lowercase letter\n'+
                            'Contains at least 1 uppercase letter')" oninput="setCustomValidity('')"
                           pattern=^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\s).*$
                           title="
                           (At least 8 characters long (and less than 100 characters)
                            Contains at least 1 number
                            Contains at least 1 lowercase letter
                            Contains at least 1 uppercase letter
                            " placeholder="Password"/>
                </p>
                <p>
                    <span class="fontawesome-lock"></span>
                    <input type="password" id="password2" name="password2" required
                           oninvalid="this.setCustomValidity('Please enter password again.')"
                           oninput="setCustomValidity('')"
                           placeholder="Password"
                           pattern=^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\s).*$
                           title="Please enter password"/>
                </p>
                <p><input type="submit" value="Log in"/></p>

                <c:if test="${not empty error}">
                    <p class="error">${error}</p>
                </c:if>
            </fieldset>
        </form>
    </div>
</tags:master>