<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:mainLayout title="Sign In" css="/css/sign.css" js="js/showPassword.js">
    <div class="input-block">
        <div class="sign-title">Sign in</div>
        <form method="POST" action="<c:url value="/signIn"/>">
            <label>Username<br>
                <input class="sign-input" type="text" name="username"/><br>
            </label>
            <c:if test="${not empty usernameError}">
                <span>${usernameError}</span><br>
            </c:if>
            <label>Password<br>
                <input class="sign-input" type="password" name="password" id="password"/><br>
            </label>
            <input type="checkbox" onclick="showPasswordOnClick()">  Show password<br>
            <c:if test="${not empty passwordError}">
                <span>${passwordError}</span><br>
            </c:if>
            <c:if test="${not empty errorMessage}">
                <span>${errorMessage}</span><br>
            </c:if>
            <button class="sign-button" type="submit">Sign In</button>
        </form>
    </div>
</t:mainLayout>
