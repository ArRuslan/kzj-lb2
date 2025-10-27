<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <jsp:attribute name="title">Login</jsp:attribute>
    <jsp:body>
        <div class="d-flex h-100 justify-content-center align-items-center">
            <div class="w-50">
                <h2 class="text-center">Login</h2>
                <form method="POST" action="${pageContext.request.contextPath}/login" class="d-flex row gap-2">
                    <input name="login" placeholder="Login" class="form-control" />
                    <input name="password" placeholder="Password" type="password" class="form-control" />

                    <button type="submit" class="btn btn-primary">Login</button>
                </form>
            </div>
        </div>
    </jsp:body>
</t:layout>
