package com.application.controller;

import com.application.data.DepartmentData;
import com.application.service.DepartmentService;
import com.application.service.impl.DefaultDepartmentService;

import java.io.IOException;
import java.util.List;
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
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/departments-list.jsp").forward(request, response);
    }
}
