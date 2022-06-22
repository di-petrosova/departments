package com.application.controller;

import com.application.dao.EmployeesDAO;
import com.application.dao.impl.DefaultEmployeesDAO;
import com.application.data.DepartmentData;
import com.application.data.EmployeeData;
import com.application.service.DepartmentService;
import com.application.service.EmployeeService;
import com.application.service.impl.DefaultDepartmentService;
import com.application.service.impl.DefaultEmployeeService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.application.controller.CoreConstants.EMPLOYEES;

public class EmpController implements Controller {

    private EmployeeService employeeService = new DefaultEmployeeService();
    private EmployeesDAO employeesDAO = new DefaultEmployeesDAO();
    private DepartmentService departmentService = new DefaultDepartmentService();
    List<DepartmentData> allDepartments = departmentService.getAllDepartments();
    @Override
    public void processGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idToEdit = req.getParameter("idToEdit");
        EmployeeData currentEmployee = employeeService.getEmployeeById(idToEdit);

        if (EMPLOYEES.equals(req.getServletPath())) {
            List<EmployeeData> allEmployees = employeeService.getAllEmployees();
            req.setAttribute("employees", allEmployees);
            req.getRequestDispatcher("/WEB-INF/jsp/employee-list.jsp").forward(req, resp);
        }
        if ("/employee/edit".equals(req.getServletPath())) {
            req.setAttribute("departments", allDepartments);
            req.setAttribute("currentEmployee", currentEmployee);
            req.getRequestDispatcher("/WEB-INF/jsp/create-edit-employee.jsp").forward(req, resp);
        }
        if ("/employee/create".equals(req.getServletPath()))
        {
            req.setAttribute("departments", allDepartments);
            req.setAttribute("currentEmployee", currentEmployee);
            req.getRequestDispatcher("/WEB-INF/jsp/create-edit-employee.jsp").forward(req, resp);
        }
    }

    @Override
    public void processPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idToEdit = req.getParameter("id");
        req.setAttribute("existingDepartmentId", employeeService.checkExistingDepartmentId(idToEdit));
        EmployeeData currentEmployee = employeeService.getEmployeeById(idToEdit);
        if (EMPLOYEES.equals(req.getServletPath())) {
            req.getRequestDispatcher("/WEB-INF/jsp/employee-list.jsp").forward(req, resp);
        }
        if ("/employee/edit".equals(req.getServletPath())) {

            String email = req.getParameter("empEmail");
            EmployeeData employeeById = employeeService.getEmployeeById(idToEdit);


            if (employeeService.checkExistingEmployeeEmail(email)) {
                req.setAttribute("departments", allDepartments);
                req.setAttribute("wrongEmail", email);
                req.setAttribute("editedEmployee", employeeService.getEmployeeById(idToEdit));
                req.setAttribute("currentEmployee", currentEmployee);
                if (email.equals(employeeById.getEmpEmail())) {
                    employeeService.editEmployee(req);
                    resp.sendRedirect(req.getContextPath() + EMPLOYEES);
                } else {
                    req.getRequestDispatcher("/WEB-INF/jsp/create-edit-employee.jsp").forward(req, resp);
                }
            } else {
                req.setAttribute("employeeId", employeeService.getRandomId());
                employeeService.editEmployee(req);
                resp.sendRedirect(req.getContextPath() + EMPLOYEES);
            }
        }
        if ("/employee/create".equals(req.getServletPath())) {
            String email = req.getParameter("empEmail");
            if(employeeService.checkExistingEmployeeEmail(email)) {
                req.setAttribute("departments", allDepartments);
                req.setAttribute("wrongEmail", email);
                req.setAttribute("createdEmployee", employeeService.convertRequestToEmployee(req));
                req.getRequestDispatcher("/WEB-INF/jsp/create-edit-employee.jsp").forward(req, resp);
            } else {
                req.setAttribute("employeeId", employeeService.getRandomId());
                employeeService.createEmployee(req);
                resp.sendRedirect(req.getContextPath() + EMPLOYEES);
            }
        }
    }
}
