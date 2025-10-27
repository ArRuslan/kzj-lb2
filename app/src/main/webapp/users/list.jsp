<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"
         import="ua.nure.kz.entities.User.Role" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <jsp:attribute name="title">Users</jsp:attribute>
    <jsp:body>
        <c:if test="${sessionScope.user.getRole() == Role.ADMIN}">
            <h3>Add user</h3>
            <form method="POST" action="${pageContext.request.contextPath}/users" class="d-flex col gap-2">
                <input name="login" placeholder="Login" class="form-control" />
                <input name="password" placeholder="Password" type="password" class="form-control" />
                <input name="fullName" placeholder="Full Name" class="form-control" />
                <input name="role" placeholder="Role" class="form-control" />

                <button type="submit" class="btn btn-primary">Create</button>
            </form>
        </c:if>

        <h2>Users</h2>

        <table class="table mt-2">
            <thead>
            <tr>
                <th scope="col">Id</th>
                <th scope="col">Login</th>
                <th scope="col">Full Name</th>
                <th scope="col">Groups</th>
                <c:if test="${sessionScope.user.getRole() == Role.ADMIN}">
                    <th scope="col">Actions</th>
                </c:if>
            </tr>
            </thead>

            <tbody>
            <c:forEach items="${users}" var="user">
                <tr>
                    <td scope="row">${user.getId()}</td>
                    <td>${user.getLogin()}</td>
                    <td>${user.getFullName()}</td>
                    <td>
                        <c:forEach items="${userGroups.get(user)}" var="group" varStatus="status">
                            <a href="${pageContext.request.contextPath}/users?groupId=${group.getId()}">${group.getName()}</a>
                            <c:if test="${!status.last}">
                                ,
                            </c:if>
                        </c:forEach>
                    </td>
                    <c:if test="${sessionScope.user.getRole() == Role.ADMIN}">
                        <td>
                            <a class="btn btn-primary" href="${pageContext.request.contextPath}/users/edit/${user.getId()}">Edit</a>
                            <a class="btn btn-danger" href="${pageContext.request.contextPath}/users/delete/${user.getId()}">Delete</a>
                        </td>
                    </c:if>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </jsp:body>
</t:layout>
