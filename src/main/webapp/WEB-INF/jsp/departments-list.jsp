<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Departments</title>
    <link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/css/bootstrap.min.css'>
</head>
<body style="box-sizing: border-box">
<table class="table table-striped table-bordered table-hover m-5" style="max-width: 100%; width: auto;">
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
        <th/>
        <th/>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="department" items="${departments}">
        <tr>
            <td>${department.id}</td>
            <td>${department.name}</td>
            <td>${department.address}</td>
            <td>
                <form action="${pageContext.request.contextPath}/department/edit" method="get">
                    <input name="idToEdit" type="text" value="${department.id}" hidden>
                    <button type="submit" class="btn btn-primary">Edit</button>
                </form>
            </td>
            <td>
                <form action="${pageContext.request.contextPath}/departments" method="post">
                    <input name="idToRemove" type="text" value="${department.id}" hidden>
                    <button type="submit" class="btn btn-danger">Remove</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<form action="${pageContext.request.contextPath}/department/create" method="get" class="m-5">
    <button type="submit" class="btn btn-primary">Create department</button>
</form>
</body>
</html>
