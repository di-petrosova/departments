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
<form action="${pageContext.request.contextPath}/employee/edit" method="post" class="m-5">
    </c:if>

    <c:if test="${uri eq create}">
    <form action="${pageContext.request.contextPath}/employee/create" method="post" class="m-5">
        </c:if>

        <c:if test="${uri eq edit}">
            <input type="text" id="id" name="id" value="${currentEmployee.id}" hidden>
        </c:if>
        <c:if test="${uri eq create}">
            <input type="text" id="id" name="id" value="${employeeId}" hidden>
        </c:if>
        <input type="text" id="createdDate" name="createdDate" value="${currentEmployee.createdDate}" hidden>
        <input type="text" id="modifiedDate" name="modifiedDate" value="${currentEmployee.modifiedDate}" hidden>
        <input type="text" id="empAge" name="empAge" value="${currentEmployee.age}" hidden>
        <p>
            <label for="firstName">First Name</label>
            <c:if test="${uri eq create}"><input type="text" id="firstName" name="firstName"
                                                 value="${createdEmployee.firstName}"></c:if>
            <c:if test="${uri eq edit}"><input type="text" id="firstName" name="firstName"
                                               value="${currentEmployee.firstName}"></c:if>
        </p>
        <p>
            <label for="lastName">Last Name</label>

            <c:if test="${uri eq create}"><input type="text" id="lastName" name="lastName"
                                                 value="${createdEmployee.lastName}"></c:if>
            <c:if test="${uri eq edit}"><input type="text" id="lastName" name="lastName"
                                               value="${currentEmployee.lastName}"></c:if>
        </p>
        <p>
            <label for="dateBirth">Date Birth</label>

            <c:if test="${uri eq create}"><input type="text" id="dateBirth" name="dateBirth"
                                                 value="${createdEmployee.dateBirth}"></c:if>
            <c:if test="${uri eq edit}"><input type="text" id="dateBirth" name="dateBirth"
                                               value="${currentEmployee.dateBirth}"></c:if>
        </p>


        <p>
            <label for="dateBirth">Email</label>

            <c:if test="${uri eq create}">
                <input type="text" id="email" name="email" value="${createdEmployee.email}">
                <c:if test="${not empty wrongEmail}">
                    <span>This email ${wrongEmail} is busy</span>
                </c:if>
            </c:if>
            <c:if test="${uri eq edit}">
                <input type="text" id="email" name="email" value="${currentEmployee.email}">
                <c:if test="${not empty wrongEmail}">
                    <span>This email ${wrongEmail} is busy</span>
                </c:if>
            </c:if>
        </p>

        <c:if test="${uri eq create}">
            <p>
                <label for="createdEmpExperience">Experience</label>
                <select name="empExperience" id="createdEmpExperience">
                    <option value="true"
                            <c:if test="${createdEmployee.experience eq true}">selected</c:if>
                    >true
                    </option>
                    <option value="false"
                            <c:if test="${createdEmployee.experience eq false}">selected</c:if>
                    >false
                    </option>
                </select>

            </p>
        </c:if>
        <c:if test="${uri eq edit}">
            <p>
                <label for="experience">Experience</label>
                <select name="experience" id="experience">
                    <option value="true"
                            <c:if test="${currentEmployee.experience eq true}">selected</c:if>
                    >true
                    </option>
                    <option value="false"
                            <c:if test="${currentEmployee.experience eq false}">selected</c:if>
                    >false
                    </option>
                </select>

            </p>
        </c:if>

        <c:if test="${uri eq create}">
            <p>
                <label for="createdEmployeeDepartmentId">Department Id</label>
                <select name="departmentId" id="createdEmployeeDepartmentId">
                    <c:forEach items="${departments}" var="department">

                        <option value="${department.id}"
                                <c:if test="${createdEmployee.departmentId eq department.id}">selected</c:if>
                        >
                                ${department.id}
                        </option>
                    </c:forEach>
                </select>
            </p>
        </c:if>

        <c:if test="${uri eq edit}">
            <p>
                <label for="departmentId">Department Id</label>
                <select name="departmentId" id="departmentId">
                    <c:forEach items="${departments}" var="department">

                        <option value="${department.id}"
                                <c:if test="${currentEmployee.departmentId.id eq department.id}">selected</c:if>>
                                ${department.id}
                        </option>
                    </c:forEach>
                </select>
            </p>
        </c:if>

        <c:if test="${uri eq create}">
            <button type="submit" class="btn btn-primary">Create</button>
        </c:if>
        <c:if test="${uri eq edit}">
            <button type="submit" class="btn btn-primary">Save</button>
        </c:if>
    </form>

        <c:if test="${uri eq edit}">
        <form action="${pageContext.request.contextPath}/employee/edit-photo?id=${currentEmployee.id}" method="post" enctype="multipart/form-data" class="m-5">

            <p>
                <label for="photo">Photo</label>
                <input type="file" id="photo" name="photo" value="${currentEmployee.photo}">
            </p>
            <button type="submit" class="btn btn-primary">Edit image</button>

        </form>
        </c:if>
</body>
</html>
