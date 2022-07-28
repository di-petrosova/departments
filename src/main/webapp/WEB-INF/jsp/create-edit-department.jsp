<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create department</title>
    <link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/css/bootstrap.min.css'>
</head>
<body class="m-5">
<c:set var="uri" value="${requestScope['javax.servlet.forward.request_uri']}"></c:set>
<c:set var="edit" value="/newdep/department/edit"></c:set>
<c:set var="create" value="/newdep/department/create"></c:set>

<c:forEach var="error" items="${message}">
    <p class="error">${error.value}</p>
</c:forEach>

    <c:if test="${uri eq edit}">
        <form action="${pageContext.request.contextPath}/department/edit" method="post">
    </c:if>

    <c:if test="${uri eq create}">
        <form action="${pageContext.request.contextPath}/department/create" method="post">
    </c:if>

        <input type="text" id="pk" name="pk" value="${currentDepartment.pk}" hidden>
        <input type="text" id="id" name="id" value="${currentDepartment.id}" hidden>
        <p>
            <label for="departmentName">Name</label>

            <c:if test="${uri eq edit}"><input type="text" id="departmentName" name="name"
                                               value="${currentDepartment.name}"></c:if>
            <c:if test="${uri eq create}"><input type="text" id="departmentName" name="name"
                                                 value="${oneDepartment.name}"></c:if>
        </p>
        <p>
            <label for="departmentAddress">Department Address</label>

            <c:if test="${uri eq edit}"><input type="text" id="departmentAddress" name="address"
                                                <c:if test="${oneDepartment.address ne 0}">value="${oneDepartment.address}"</c:if>>
            </c:if>
            <c:if test="${uri eq create}"><input type="text" id="departmentAddress" name="address"
                                                 <c:if test="${oneDepartment.address ne 0}">value="${oneDepartment.address}"</c:if>>
            </c:if>
        </p>

        <c:if test="${uri eq edit}">
            <button type="submit"  class="btn btn-primary">Save</button>
        </c:if>

        <c:if test="${uri eq create}">
            <button type="submit"  class="btn btn-primary">Create department</button>
        </c:if>
    </form>

</body>
</html>