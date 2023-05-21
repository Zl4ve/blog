<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:mainLayout title="Success" css="css/sign.css">
    <span>You registered</span>
    <a href="<c:url value="/signIn"/>">Go to login page</a>
</t:mainLayout>