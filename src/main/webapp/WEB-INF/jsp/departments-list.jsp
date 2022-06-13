<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Departments</title>
    <link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/css/bootstrap.min.css'>
</head>
<body>
<table class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th>
            Department Id
        </th>
        <th>
            Department Name
        </th>
        <th>
            Department Address (Index)
        </th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="department" items="${departments}">
        <tr>
            <td>${department.id}</td>
            <td>${department.name}</td>
            <td>${department.adress}</td>
        </tr>
    </c:forEach>
    <tr>
        <td>
            <form action="${pageContext.request.contextPath}/department/create" method="get">
                <button type="submit">Create department</button>
            </form>
        </td>
    </tr>
    </tbody>
</table>
</body>
</html>
