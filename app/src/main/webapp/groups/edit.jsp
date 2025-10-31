<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <jsp:attribute name="title">Edit user ${group.getName()}</jsp:attribute>
    <jsp:body>
        <c:if test="${not empty error}">
            <h3 style="color: red;">${error}</h3>
        </c:if>

        <form method="POST" action="${pageContext.request.contextPath}/groups/edit/${group.getId()}" class="d-flex row gap-2">
            <input name="name" placeholder="Name" value="${group.getName()}" class="form-control" />

            <div class="btn-group d-flex" role="group">
                <a role="button" href="${pageContext.request.contextPath}/groups" class="btn btn-danger w-100">Cancel</a>
                <button type="submit" class="btn btn-warning w-100">Edit</button>
            </div>
        </form>
    </jsp:body>
</t:layout>