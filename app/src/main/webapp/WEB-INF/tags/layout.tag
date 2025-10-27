<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ attribute name="title" fragment="true" %>

<html>
<head>
    <title>
        <jsp:invoke fragment="title"/>
    </title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI"
            crossorigin="anonymous"></script>
</head>
<body>

<div class="container-fluid p-3 h-100">
    <div class="row h-100">
        <c:if test="${sessionScope.user != null}">
            <div class="col flex-grow-0">
                <div class="d-flex flex-column flex-shrink-0 p-3 bg-light" style="width: 200px;">
                    <a href="${pageContext.request.contextPath}" class="d-flex align-items-center mb-3 mb-md-0 me-md-auto link-dark text-decoration-none">
                        <span class="fs-4">Kzj Lb2</span>
                    </a>
                    <hr>
                    <ul class="nav nav-pills flex-column mb-auto">
                        <li class="nav-item">
                            <a href="${pageContext.request.contextPath}/users" class="nav-link link-dark">
                                Users
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="${pageContext.request.contextPath}/groups" class="nav-link link-dark">
                                Groups
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </c:if>
        <div class="col flex-grow-1 h-100">
            <c:if test="${sessionScope.user != null}">
                <div class="text-end d-flex gap-1 justify-content-end">
                    <p>
                        You are logged in as user
                        [${sessionScope.user.getRole().toString()}] <b>${sessionScope.user.getFullName()}</b>
                    </p>
                    <p>
                        <a href="${pageContext.request.contextPath}/logout">Logout</a>
                    </p>
                </div>
            </c:if>
            <jsp:doBody/>
        </div>
    </div>
</div>

</body>
</html>
