<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"
         import="ua.nure.kz.entities.User.Role" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js" integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI" crossorigin="anonymous"></script>
</head>
<body>
You are logged in as user [${sessionScope.user.getRole().toString()}] <b>${sessionScope.user.getFullName()}</b> <a href="logout">Logout</a>

<h2>Users</h2>

<c:if test="${sessionScope.user.getRole() == Role.ADMIN}">
    <h3>Add user</h3>
    <form method="POST" action="login" class="d-flex col gap-2">
        <input name="login" placeholder="Login" />
        <input name="password" placeholder="Password" type="password" />
        <input name="fullName" placeholder="Full Name" />
        <input name="role" placeholder="Role" />

        <button type="submit" class="btn btn-primary">Create</button>
    </form>
</c:if>

<table class="table mt-2">
    <thead>
    <tr>
        <th scope="col">Id</th>
        <th scope="col">Login</th>
        <th scope="col">Full Name</th>
        <th scope="col">Groups</th>
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
            ${group.getName()}
            <c:if test="${!status.last}">
                ,
            </c:if>
        </c:forEach>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>
