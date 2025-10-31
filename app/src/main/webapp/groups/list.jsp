<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"
         import="ua.nure.kz.entities.User.Role" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <jsp:attribute name="title">Groups</jsp:attribute>
    <jsp:body>
        <c:if test="${not empty error}">
            <h3 style="color: red;">${error}</h3>
        </c:if>

        <c:if test="${sessionScope.user.getRole() == Role.ADMIN}">
            <h3>Add group</h3>
            <form method="POST" action="${pageContext.request.contextPath}/groups" class="d-flex col gap-2">
                <input name="name" placeholder="Name" class="form-control" />

                <button type="submit" class="btn btn-primary">Create</button>
            </form>
        </c:if>

        <h2>Groups</h2>

        <table class="table mt-2">
            <thead>
            <tr>
                <th scope="col">Id</th>
                <th scope="col">Name</th>
                <c:if test="${sessionScope.user.getRole() == Role.ADMIN}">
                    <th scope="col">Actions</th>
                </c:if>
            </tr>
            </thead>

            <tbody>
            <c:forEach items="${groups}" var="group">
                <tr>
                    <td scope="row">${group.getId()}</td>
                    <td>${group.getName()}</td>
                    <c:if test="${sessionScope.user.getRole() == Role.ADMIN}">
                        <td>
                            <a class="btn btn-primary" href="${pageContext.request.contextPath}/groups/edit/${group.getId()}">Edit</a>
                            <a class="btn btn-danger" href="${pageContext.request.contextPath}/groups/delete/${group.getId()}">Delete</a>
                        </td>
                    </c:if>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </jsp:body>
</t:layout>
