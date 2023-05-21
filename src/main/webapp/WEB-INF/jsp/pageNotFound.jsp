<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:mainLayout title="Page not found :(" css="css/sign.css" js="js/randomErrorImage.js">
    <img src="<c:url value="img/404image.jpg"/>"><br>
    Requested page not found :(
</t:mainLayout>
