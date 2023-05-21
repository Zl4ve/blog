<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:mainLayout title="Create post" css="css/postSuggest.css">
    <div class="suggest-container">
        <div class="post-suggest">
            <div class="post-suggest-title">Suggest post</div>
            <form method="post" action="<c:url value="/createPost"/>">
                <div class="post-suggest-title-form">
                    <label>Title<br>
                        <input type="text" name="title"/><br>
                    </label><br>
                </div>
                <c:if test="${not empty titleError}">
                    <span>${titleError}</span><br>
                </c:if>
                <div class="post-suggest-textarea">
                    <label>
                        <textarea name="text" placeholder="Type..." rows="10"></textarea><br>
                    </label>
                </div>
                <c:if test="${not empty textError}">
                    <span>${textError}</span><br>
                </c:if>
                <input type="submit" class="post-suggest-submit-button">
            </form>
        </div>
    </div>
</t:mainLayout>
