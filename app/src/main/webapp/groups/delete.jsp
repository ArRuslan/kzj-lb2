<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <jsp:attribute name="title">Delete group ${group.getName()}</jsp:attribute>
    <jsp:body>
        <h2>Are you sure you want to delete user ${group.getName()}?</h2>

        <form action="" method="POST">
            <div class="btn-group d-flex" role="group">
                <a role="button" href="${pageContext.request.contextPath}/groups" class="btn btn-danger w-100">Cancel</a>
                <button type="submit" class="btn btn-warning w-100">Delete</button>
            </div>
        </form>
    </jsp:body>
</t:layout>
