<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:mainLayout title="Sign Up" css="css/sign.css" js="js/showPassword.js">
    <div class="input-block">
        <div class="sign-title">Registration</div>
        <form method="POST" action="<c:url value='/signUp'/>">
            <label>Username<br>
                <input class="sign-input" type="text" name="username"/><br>
            </label>
            <c:if test="${not empty usernameError}">
                <span>${usernameError}</span><br>
            </c:if>
            <c:if test="${not empty usernameAlreadyExistsError}">
                <span>${usernameAlreadyExistsError}</span><br>
            </c:if>
            <label>Password<br>
                <input class="sign-input" type="password" name="password" id="password"/><br>
            </label>
            <c:if test="${not empty passwordError}">
                <span>${passwordError}</span><br>
            </c:if>
            <input type="checkbox" onclick="showPasswordOnClick()">  Show password<br>
            <label>E-mail<br>
                <input class="sign-input" type="text" name="email"/><br>
            </label>
            <c:if test="${not empty emailError}">
                <span>${emailError}</span><br>
            </c:if>
            <c:if test="${not empty emailAlreadyExistsError}">
                <span>${emailAlreadyExistsError}</span><br>
            </c:if>
            <button class="sign-button" type="submit">Sign up</button>
        </form>
    </div>
</t:mainLayout>
