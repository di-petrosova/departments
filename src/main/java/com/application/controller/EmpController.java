package com.application.controller;

import com.application.data.EmployeeData;
import com.application.service.EmployeeService;
import com.application.service.impl.DefaultEmployeeService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.application.controller.CoreConstants.EMPLOYEES;

public class EmpController implements Controller {

    private EmployeeService employeeService = new DefaultEmployeeService();

    @Override
    public void processGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (EMPLOYEES.equals(req.getServletPath())) {
            List<EmployeeData> allEmployees = employeeService.getAllEmployees();
            req.setAttribute("employees", allEmployees);
            req.getRequestDispatcher("/WEB-INF/jsp/employee-list.jsp").forward(req, resp);
        }
    }

    @Override
    public void processPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (EMPLOYEES.equals(req.getServletPath())) {
            req.getRequestDispatcher("/WEB-INF/jsp/employee-list.jsp").forward(req, resp);
        }
    }
}
