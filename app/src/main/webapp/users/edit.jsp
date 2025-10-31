<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <jsp:attribute name="title">Edit user ${user.getLogin()}</jsp:attribute>
    <jsp:body>
        <h2>Edit user ${user.getLogin()}</h2>

        <c:if test="${not empty error}">
            <h3 style="color: red;">${error}</h3>
        </c:if>

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

        <h3>Groups<h3>

        <h4>Add to group</h4>
        <form method="POST" action="${pageContext.request.contextPath}/users/add-group" class="d-flex col gap-2">
            <input type="hidden" name="userId" value="${user.getId()}" />
            <input name="groupName" placeholder="Group Name" class="form-control" />

            <button type="submit" class="btn btn-primary">Add</button>
        </form>

        <table class="table mt-2">
            <thead>
            <tr>
                <th scope="col">Name</th>
                <th scope="col">Remove</th>
            </tr>
            </thead>

            <tbody>
            <c:forEach items="${userGroups}" var="group">
                <tr>
                    <td scope="row" class="align-middle">${group.getName()}</td>
                    <td>
                        <form action="${pageContext.request.contextPath}/users/remove-group" method="POST" class="d-inline">
                            <input type="hidden" name="userId" value="${user.getId()}" />
                            <input type="hidden" name="groupId" value="${group.getId()}" />
                            <button class="btn btn-danger">Remove</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </jsp:body>
</t:layout>