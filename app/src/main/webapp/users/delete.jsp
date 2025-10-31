<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <jsp:attribute name="title">Delete user ${user.getLogin()}</jsp:attribute>
    <jsp:body>
        <h2>Are you sure you want to delete user ${user.getLogin()}?</h2>

        <c:if test="${not empty error}">
            <h3 style="color: red;">${error}</h3>
        </c:if>

        <form action="" method="POST">
            <div class="btn-group d-flex" role="group">
                <a role="button" href="${pageContext.request.contextPath}/users" class="btn btn-danger w-100">Cancel</a>
                <button type="submit" class="btn btn-warning w-100">Delete</button>
            </div>
        </form>
    </jsp:body>
</t:layout>
