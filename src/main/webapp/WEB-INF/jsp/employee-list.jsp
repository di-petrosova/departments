<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Employees</title>
    <link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/css/bootstrap.min.css'>
</head>
<body>
<table class="table table-striped table-bordered table-hover m-5" style="max-width: 100%; width: auto;">
    <thead>
    <tr>
        <th>
            Employee Id
        </th>
        <th>
            First Name
        </th>
        <th>
            Last Name
        </th>
        <th>
            Date Birth
        </th>
        <th>
            Age
        </th>
        <th>
            Email
        </th>
        <th>
            Photo
        </th>
        <th>
            Created date
        </th>
        <th>
            Modified date
        </th>
        <th>
            Experience
        </th>
        <th>
            Department Id
        </th>
        <th>
            Temp
        </th>
        <th/>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="employee" items="${employees}">
        <tr>
            <td>${employee.id}</td>
            <td>${employee.firstName}</td>
            <td>${employee.lastName}</td>
            <td>${employee.dateBirth}</td>
            <td>${employee.age}</td>
            <td>${employee.email}</td>
            <td><img src="${employee.photo}" alt=""></td>
            <td>${employee.createdDate}</td>
            <td>${employee.modifiedDate}</td>
            <td>${employee.experience}</td>
            <td>
                <c:forEach var="subItem" items="${departments}">
                    <c:if test="${subItem.id == employee.departmentId.id}">
                        ${subItem.id}
                    </c:if>
                </c:forEach>
            </td>
<%--            <td><img style="width: 100px; height: auto" src="data:image/jpeg;base64,${employee.tempphoto}"/></td>--%>
            <td><img  style="width: 100px; height: auto" src="${pageContext.request.contextPath}/employee/photo/${employee.id}.jpeg"/></td>
            <td>
                <form action="${pageContext.request.contextPath}/employee/edit" method="get">
                    <input name="idToEdit" type="text" value="${employee.id}" hidden>
                    <button class="btn btn-primary">Edit</button>
                </form>
            </td>
            <td>
                <form action="${pageContext.request.contextPath}/employees" method="post">
                    <input name="idToRemove" type="text" value="${employee.id}" hidden>
                    <button class="btn btn-danger">Remove</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<form action="${pageContext.request.contextPath}/employee/create" method="get" class="m-5">
    <button type="submit" class="btn btn-primary">Create new employee</button>
</form>
</body>
</html>
