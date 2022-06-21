<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Create/edit employee</title>
    <link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/css/bootstrap.min.css'>
</head>
<body>
<c:set var="uri" value="${requestScope['javax.servlet.forward.request_uri']}"></c:set>
<c:set var="edit" value="/newdep/employee/edit"></c:set>
<c:set var="create" value="/newdep/employee/create"></c:set>

<c:if test="${uri eq edit}">
<form action="${pageContext.request.contextPath}/employee/edit" method="post">
    </c:if>

    <c:if test="${uri eq create}">
    <form action="${pageContext.request.contextPath}/employee/create" method="post">
        </c:if>

        <input type="text" id="id" name="id" value="${currentEmployee.empId}" hidden>
        <input type="text" id="createdDate" name="createdDate" value="${currentEmployee.createdDate}" hidden>
        <input type="text" id="modifiedDate" name="modifiedDate" value="${currentEmployee.modifiedDate}" hidden>
        <input type="text" id="empAge" name="empAge" value="${currentEmployee.empAge}" hidden>
        <p>
            <label for="empFirstName">First Name</label>
            <c:if test="${uri eq edit}"><input type="text" id="empFirstName" name="empFirstName"
                                               value="${currentEmployee.empFirstName}"></c:if>
        </p>
        <p>
            <label for="empLastName">Last Name</label>

            <c:if test="${uri eq edit}"><input type="text" id="empLastName" name="empLastName"
                                               value="${currentEmployee.empLastName}"></c:if>
        </p>
        <p>
            <label for="dateBirth">Date Birth</label>

            <c:if test="${uri eq edit}"><input type="text" id="dateBirth" name="dateBirth"
                                               value="${currentEmployee.dateBirth}"></c:if>
        </p>


        <p>
            <label for="dateBirth">Email</label>

            <c:if test="${uri eq edit}"><input type="text" id="empEmail" name="empEmail"
                                               value="${currentEmployee.empEmail}"></c:if>
        </p>
        <p>
            <label for="photo">Photo</label>

            <c:if test="${uri eq edit}"><input type="text" id="photo" name="photo"
                                               value="${currentEmployee.photo}"></c:if>
        </p>


        <p>
            <label for="empExperience">Experience</label>
            <select name="empExperience" id="empExperience">
                <option value="true"
                        <c:if test="${currentEmployee.empExperience eq true}">selected</c:if>
                >true
                </option>
                <option value="false"
                        <c:if test="${currentEmployee.empExperience eq false}">selected</c:if>
                >false
                </option>
            </select>

            <%-- <c:if test="${uri eq edit}"><input type="text" id="empExperience" name="empExperience"
                                                   value="${currentEmployee.empExperience}"></c:if>--%>
        </p>
        <p>
            <label for="departmentId">Department Id</label>
            <select name="departmentId" id="departmentId">
                <c:forEach items="${departments}" var="department">

                    <option value="${department.id}"
                            <c:if test="${currentEmployee.departmentId eq department.id}">selected</c:if>>
                            ${department.id}
                    </option>
                </c:forEach>
            </select>
            <%--            <c:if test="${uri eq edit}"><input type="text" id="departmentId" name="departmentId"--%>
            <%--                                               value="${currentEmployee.departmentId}"></c:if>--%>
        </p>

        <c:if test="${uri eq edit}">
            <button type="submit" class="btn btn-primary">Save</button>
        </c:if>
    </form>

</body>
</html>
