<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:mainLayout title="Anime blog" css="/css/postPreview.css">
    <div class="posts">
        <c:forEach items="${posts}" var="post" varStatus="loop">
            <div class="post-preview">
                <div class="title-text">
                    <a href="<c:url value="/post?id=${post.id}"/>">${post.title}</a>
                </div>
                <c:if test="${firstPictures[loop.index] != ''}">
                    <img src="${pageContext.request.contextPath}/image${firstPictures[loop.index].substring(firstPictures[loop.index].lastIndexOf("/"), firstPictures[loop.index].length())}">
                </c:if>
            </div>
        </c:forEach>
    </div>
</t:mainLayout>
