package com.application.controller;

import com.application.data.DepartmentData;
import com.application.service.DepartmentService;
import com.application.service.impl.DefaultDepartmentService;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DepController extends HttpServlet {
    private DepartmentService departmentService = new DefaultDepartmentService();
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {
        if ("/departments".equals(request.getServletPath())) {
            List<DepartmentData> allDepartments = departmentService.getAllDepartments();
            request.setAttribute("departments", allDepartments);
            request.getRequestDispatcher("/WEB-INF/jsp/departments-list.jsp").forward(request, response);
        }

        if("/department/create".equals(request.getServletPath())) {
            request.getRequestDispatcher("/WEB-INF/jsp/create-edit-department.jsp").forward(request, response);
        }

        if("/department/edit".equals(request.getServletPath())) {
            String idToEdit = request.getParameter("idToEdit");
            DepartmentData departmentById = departmentService.getDepartmentById(idToEdit);
            if(Objects.nonNull(departmentById)) {
                request.setAttribute("currentDepartment", departmentService.getDepartmentById(idToEdit));
            }
            request.getRequestDispatcher("/WEB-INF/jsp/create-edit-department.jsp").forward(request, response);
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ("/departments".equals(request.getServletPath())) {
            String idToRemove = request.getParameter("idToRemove");
            List<DepartmentData> leftDepartments = departmentService.removeDepartment(idToRemove);

            request.setAttribute("departments", leftDepartments);
            request.getRequestDispatcher("/WEB-INF/jsp/departments-list.jsp").forward(request, response);
        }

        if("/department/create".equals(request.getServletPath())) {
            departmentService.createDepartment(request);
            response.sendRedirect(request.getContextPath() + "/departments");
        }

        if("/department/edit".equals(request.getServletPath())) {

            departmentService.editDepartment(request);
            response.sendRedirect(request.getContextPath() + "/departments");
        }
    }
}
