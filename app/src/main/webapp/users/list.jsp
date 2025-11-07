<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"
         import="ua.nure.kz.entities.User.Role" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<!--
<jsp :useBean id="user" scope="session" type="ua.nure.kz.entities.User"/>
<jsp :useBean id="error" scope="request" type="java.lang.String"/>
<jsp :useBean id="users" scope="request" type="java.lang.Iterable<ua.nure.kz.entities.User>"/>
<jsp :useBean id="userGroups" scope="request" type="java.util.Map<java.lang.Long, ua.nure.kz.entities.Group>"/>
<jsp :useBean id="pagination" scope="request" type="ua.nure.kz.utils.Pagination"/>
-->

<t:layout>
    <jsp:attribute name="title">Users</jsp:attribute>
    <jsp:body>
        <c:if test="${not empty error}">
            <h3 style="color: red;">${error}</h3>
        </c:if>

        <c:if test="${sessionScope.user.role eq Role.ADMIN}">
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
                <c:if test="${sessionScope.user.role eq Role.ADMIN}">
                    <th scope="col">Actions</th>
                </c:if>
            </tr>
            </thead>

            <tbody>
            <c:forEach items="${users}" var="user">
                <tr>
                    <td scope="row">${user.id}</td>
                    <td>${user.login}</td>
                    <td>${user.fullName}</td>
                    <td>
                        <c:forEach items="${userGroups.get(user.id)}" var="group" varStatus="status">
                            <a href="${pageContext.request.contextPath}/users?groupId=${group.id}">${group.name}</a>
                            <c:if test="${!status.last}">
                                ,
                            </c:if>
                        </c:forEach>
                    </td>
                    <c:if test="${sessionScope.user.role eq Role.ADMIN}">
                        <td>
                            <a class="btn btn-primary" href="${pageContext.request.contextPath}/users/edit/${user.id}">Edit</a>
                            <a class="btn btn-danger" href="${pageContext.request.contextPath}/users/delete/${user.id}">Delete</a>
                        </td>
                    </c:if>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <t:pagination
            hasPrev="${pagination.hasPrev}"
            hasNext="${pagination.hasNext}"
            pages="${pagination.pages}"
            currentPath="${pagination.currentPath}"
            currentQuery="${pagination.currentQuery}"
            prevPage="${pagination.prevPage}"
            nextPage="${pagination.nextPage}"
            pageSize="${pagination.pageSize}"
        />
    </jsp:body>
</t:layout>
