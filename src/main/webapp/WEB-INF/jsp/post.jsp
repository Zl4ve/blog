<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:mainLayout title="${post.title}" css="/css/post.css">
    <div class="container">
        <div class="current-post">
            <div class="post-title">
                ${post.title}
            </div>
            <div class="post-content">
                <c:forEach items="${postContent}" var="item" varStatus="loop">
                    <c:if test="${loop.index % 2 == 0}">
                        <p>${item}</p><br>
                    </c:if>
                    <c:if test="${loop.index % 2 != 0 and item != ''}">
                        <img src="${pageContext.request.contextPath}/image${item.substring(item.lastIndexOf("/"), item.length())}"><br>
                    </c:if>
                </c:forEach>
            </div>
            <div class="author-info">
                Author: <a href="<c:url value="/profile?id=${author.id}"/>">${author.username}</a>
            </div>

            <c:if test="${sessionScope.account != null and post.status == 'accepted'}">
                <div class="post-comment">
                    <form method="post" action="<c:url value="/post?id=${post.id}"/>">
                        <label>
                            <textarea class="post-comment-text" type="text" name="comment" placeholder="Type..."></textarea><br>
                            <button class="post-comment-button" type="submit">Post comment</button>
                        </label>
                    </form>
                </div>
            </c:if>

            <c:if test="${post.status == 'accepted'}">
                <div class="comments-title">
                    Comments:
                </div>
                <div class="comments">
                    <c:forEach items="${comments}" var="comment" varStatus="loop">
                        <div class="comment">
                            <div class="comment-author">
                                <a href="<c:url value="/profile?id=${comment.authorId}"/>">${commentAuthorsUsernames[loop.index]}</a>
                            </div>
                            <div class="comment-text">
                                <p>${comment.text}</p>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:if>
        </div>
    </div>
</t:mainLayout>
