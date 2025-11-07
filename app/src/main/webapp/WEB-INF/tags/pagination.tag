<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ attribute name="hasPrev" required="true" type="java.lang.Boolean" %>
<%@ attribute name="hasNext" required="true" type="java.lang.Boolean" %>
<%@ attribute name="pages" required="true" type="ua.nure.kz.utils.PaginationPage[]" %>
<%@ attribute name="currentPath" required="true" type="java.lang.String" %>
<%@ attribute name="currentQuery" required="true" type="java.lang.String" %>
<%@ attribute name="prevPage" required="true" type="java.lang.Integer" %>
<%@ attribute name="nextPage" required="true" type="java.lang.Integer" %>
<%@ attribute name="pageSize" required="true" type="java.lang.Integer" %>

<nav>
    <ul class="pagination mb-0">
        <c:set var="prevDisabled" value="disabled"/>
        <c:set var="nextDisabled" value="disabled"/>

        <c:if test="${hasPrev}">
            <c:set var="prevDisabled" value=""/>
        </c:if>
        <c:if test="${hasNext}">
            <c:set var="nextDisabled" value=""/>
        </c:if>


        <li class="page-item">
            <a class="page-link ${prevDisabled}" href="${currentPath}?page=${prevPage}&pageSize=${pageSize}&${currentQuery}" ${prevDisabled}>
                Previous
            </a>
        </li>

        <c:forEach items="${pages}" var="page">
            <c:set var="active" value=""/>
            <c:if test="${page.isCurrent}">
                <c:set var="active" value="active"/>
            </c:if>

            <li class="page-item">
                <a class="page-link ${active}" href="${currentPath}?page=${page.num}&pageSize=${pageSize}&${currentQuery}">
                    <c:out value="${page.num}"/>
                </a>
            </li>
        </c:forEach>

        <li class="page-item">
            <a class="page-link ${nextDisabled}" href="${currentPath}?page=${nextPage}&pageSize=${pageSize}&${currentQuery}" ${nextDisabled}>
                Next
            </a>
        </li>
    </ul>
</nav>