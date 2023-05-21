<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:mainLayout title="${reqAccount.username}'s subscribers" css="css/sign.css">
    <h3>${reqAccount.username}'s subscribers</h3>
    <c:forEach items="${subscribers}" var="subscriber">
        <a href="<c:url value="/profile?id=${subscriber.id}"/>">${subscriber.username}</a>
    </c:forEach>
</t:mainLayout>
