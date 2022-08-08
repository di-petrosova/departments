<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title>Create department</title>
    <link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/css/bootstrap.min.css'>
</head>
<body class="m-5">

<c:forEach var="error" items="${message}">
    <p class="error">${error.value}</p>
</c:forEach>

<c:set var="url" value="${pageContext.request.contextPath}/departments/create"/>
<c:if test="${edit}">
    <c:set var="url" value="${pageContext.request.contextPath}/departments/edit"/>
</c:if>

<form:form action="${url}" method="post" modelAttribute="departmentForm">
    <form:hidden path="id"/>
    <p>
        <form:label path="name">Name</form:label>
        <form:input path="name"/>
    </p>

    <p>
        <form:label path="address">Address</form:label>
        <form:input path="address"/>
    </p>

    <button type="submit" class="btn btn-primary">
        <c:choose>
            <c:when test="${edit}">
                Save
            </c:when>
            <c:otherwise>
                Create department
            </c:otherwise>
        </c:choose>
    </button>

</form:form>

</body>
</html>