<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js" integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI" crossorigin="anonymous"></script>
</head>
<body>
You are logged in as user [${sessionScope.user.getRole().toString()}] <b>${sessionScope.user.getFullName()}</b> <a href="${pageContext.request.contextPath}/logout">Logout</a>

<h2>Are you sure you want to delete user ${user.getLogin()}?</h2>

<form action="" method="POST">
    <div class="btn-group d-flex" role="group">
        <a role="button" href="${pageContext.request.contextPath}/users" class="btn btn-danger w-100">Cancel</a>
        <button type="submit" class="btn btn-warning w-100">Delete</button>
    </div>
</form>

</body>
</html>
