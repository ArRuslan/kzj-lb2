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
You are logged in as user [${sessionScope.user.getRole().toString()}] <b>${sessionScope.user.getFullName()}</b> <a href="${pageContext.request.contextPath}/logout">Logout</a>

<h2>User ${user.getLogin()}</h2>

<form method="POST" action="${pageContext.request.contextPath}/users/edit/${user.getId()}" class="d-flex col gap-2">
    <input name="login" placeholder="Login" value="${user.getLogin()}"/>
    <input name="password" placeholder="Password" type="password" value="${user.getPassword()}"/>
    <input name="fullName" placeholder="Full Name" value="${user.getFullName()}"/>
    <input name="role" placeholder="Role" value="${user.getRole()}"/>

    <button type="submit" class="btn btn-primary">Edit</button>
</form>

</body>
</html>
