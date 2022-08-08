<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<body>

<c:forEach var="error" items="${message}">
    <p class="error">${error.value}</p>
</c:forEach>

<c:set var="url" value="${pageContext.request.contextPath}/employees/create"/>
<c:if test="${edit}">
    <c:set var="url" value="${pageContext.request.contextPath}/employees/edit"/>
</c:if>

<form:form action="${url}" method="post" modelAttribute="employeeForm" class="m-5">
    <form:hidden path="id"/>
    <input type="text" value="${mediaModelForm.idEmp}" name="idEmp" hidden>
    <c:if test="${edit}">
        <fmt:formatDate value="${employeeForm.createdDate}" var="formattedCreatedDate" pattern="yyyy/MM/dd HH:MM:SS"/>
        <form:hidden path="createdDate" value="${formattedCreatedDate}"/>
    </c:if>
    <p>
        <form:label path="firstName">First Name</form:label>
        <form:input path="firstName"/>
    </p>

    <p>
        <form:label path="lastName">Last Name</form:label>
        <form:input path="lastName"/>
    </p>
    <p>
        <form:label path="dateBirth">Date Birth</form:label>
        <form:input path="dateBirth"/>
    </p>
    <p>
        <form:label path="email">Email</form:label>
        <form:input path="email"/>
    </p>
    <p>
        <form:label path="experience">Experience</form:label>
        <form:select path="experience">
            <form:option value="false"/>
            <form:option value="true"/>
        </form:select>
    </p>
    <p>
        <form:label path="departmentId.id">Department Id</form:label>
        <form:select path="departmentId.id">
            <form:options items="${departments}" itemValue="id" itemLabel="id"/>
        </form:select>

    </p>

    <button type="submit" class="btn btn-primary">
        <c:choose>
            <c:when test="${edit}">
                Save
            </c:when>
            <c:otherwise>
                Create employee
            </c:otherwise>
        </c:choose>
    </button>

</form:form>


<c:if test="${edit}">
    <form:form action="${pageContext.request.contextPath}/employees/edit-photo?id=${mediaModelForm.idEmp}" method="post"
               modelAttribute="mediaModelForm" class="m-5" enctype="multipart/form-data">
        <form:hidden path="idEmp"/>
        <form:hidden path="photoId"/>
        <p>
            <form:label path="photo">Photo</form:label>
            <form:input path="photo" type="file"/>
        </p>
        <button type="submit" class="btn btn-primary">Update image</button>
    </form:form>
    <%--<form action="${pageContext.request.contextPath}/employees/edit-photo?id=${currentEmployee.id}" method="post"
          enctype="multipart/form-data" class="m-5">

        <input type="text" name="photoId" value="${currentMedia.id}" hidden>
        <p>
            <label for="photo">Photo</label>
            <input type="file" id="photo" name="photo" value="${currentEmployee.photo}">
        </p>
        <button type="submit" class="btn btn-primary">Update image</button>

    </form>--%>
</c:if>
<%-- <c:if test="${uri eq edit}">
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
                                          value="${currentEmployee.dateBirth}"></c:if>
     <c:if test="${uri eq edit}"><input type="text" id="dateBirth" name="dateBirth"
                                        value="${currentEmployee.dateBirth}"></c:if>
 </p>


 <p>
     <label for="dateBirth">Email</label>

     <c:if test="${uri eq create}">
         <input type="text" id="email" name="email" value="${currentEmployee.email}">
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
                         <c:if test="${createdEmployee.departmentId.id eq department.id}">selected</c:if>
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
 </c:if>--%>


</body>
<head>
    <title>Create/edit employee</title>
    <link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/css/bootstrap.min.css'>
</head>
</html>
