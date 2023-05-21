<%@tag description="Default Layout Tag" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@attribute name="title" required="true" type="java.lang.String" %>
<%@attribute name="css" required="true" type="java.lang.String"%>
<%@attribute name="js" required="false" type="java.lang.String"%>

<!doctype html>
<html>
<head>
    <title>${title}</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/headerFooter.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="${css}"/>"/>
    <script src="${js}" charset="UTF-8"></script>
</head>
<body>
<header>
    <nav class="navbar">
        <ul>
            <li><a href="<c:url value="/main"/>">Main page</a></li>
            <c:if test="${sessionScope.account == null}">
                <li><a href="<c:url value="/signUp"/>">Sign up</a></li>
                <li><a href="<c:url value="/signIn"/>">Sign in</a></li>
            </c:if>
            <c:if test="${sessionScope.account != null}">
                <li><a href="<c:url value="/createPost"/>">Suggest post</a></li>
                <li><a href="<c:url value="/profile?id=${sessionScope.account.id}"/>">Profile</a></li>
                <li><a href="<c:url value="/logout"/>">Logout</a></li>
                <c:set var="account" value="${sessionScope.account}"/>
                <c:if test="${sessionScope.account.role == 'admin'}">
                    <li><a href="<c:url value="/suggestedPosts"/>">Suggested posts</a></li>
                </c:if>
            </c:if>
        </ul>
    </nav>
</header>
<jsp:doBody/>
<footer>

</footer>
</body>
</html>