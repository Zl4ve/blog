<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:mainLayout title="Profile" css="css/profile.css">
    ${profileAccount.username}
    <a href="<c:url value="/subscribers?id=${profileAccount.id}"/>">Subscribers</a>
    <c:if test="${sessionScope.account.id != profileAccount.id}">
        <c:if test="${!isSubscribed}">
            <a href="<c:url value="/subscribe?id=${profileAccount.id}"/>">Subscribe</a>
        </c:if>
        <c:if test="${isSubscribed}">
            <a href="<c:url value="/unsubscribe?id=${profileAccount.id}"/>">Unsubscribe</a>
        </c:if>
    </c:if>
    <c:forEach items="${posts}" var="post">
        <a href="<c:url value="/post?id=${post.id}"/>">${post.title}</a>
    </c:forEach>
</t:mainLayout>
