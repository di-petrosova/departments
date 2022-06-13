<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create department</title>
</head>
<body>
<c:set var="uri" value="${requestScope['javax.servlet.forward.request_uri']}"></c:set>
<c:set var="edit" value="/newdep/department/edit"></c:set>
<c:set var="create" value="/newdep/department/create"></c:set>


<c:if test="${uri eq create}">
<form action="${pageContext.request.contextPath}/department/create" method="post">
    </c:if>
    <p>
        <label for="departmentName">Name</label>
        <c:if test="${uri eq create}"><input type="text" id="departmentName" name="name"></c:if>
    </p>
    <p>
        <label for="departmentAddress">Department Address</label>
        <c:if test="${uri eq create}"><input type="text" id="departmentAddress" name="adress"></c:if>
    </p>
    <c:if test="${uri eq create}">
        <button type="submit">Create department</button>
    </c:if>
</form>

</body>
</html>