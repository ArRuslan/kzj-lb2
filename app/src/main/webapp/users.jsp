<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"
         import="ua.nure.kz.entities.User.Role" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
    <style>
        .d-flex {
            display: flex;
        }

        .row {
            flex-direction: row;
        }

        .col {
            flex-direction: column;
        }

        .gap-2 {
            gap: 1.5rem;
        }
    </style>
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

        <button type="submit">Create</button>
    </form>
</c:if>

</body>
</html>
