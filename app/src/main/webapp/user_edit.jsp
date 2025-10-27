<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <jsp:attribute name="title">Edit user ${user.getLogin()}</jsp:attribute>
    <jsp:body>
        <form method="POST" action="${pageContext.request.contextPath}/users/edit/${user.getId()}" class="d-flex row gap-2">
            <input name="login" placeholder="Login" value="${user.getLogin()}" class="form-control" />
            <input name="password" placeholder="Password" type="password" value="${user.getPassword()}" class="form-control" />
            <input name="fullName" placeholder="Full Name" value="${user.getFullName()}" class="form-control" />
            <input name="role" placeholder="Role" value="${user.getRole()}" class="form-control" />

            <div class="btn-group d-flex" role="group">
                <a role="button" href="${pageContext.request.contextPath}/users" class="btn btn-danger w-100">Cancel</a>
                <button type="submit" class="btn btn-warning w-100">Edit</button>
            </div>
        </form>
    </jsp:body>
</t:layout>