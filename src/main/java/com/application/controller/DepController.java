package com.application.controller;

import com.application.dao.DepartmentsDAO;
import com.application.dao.impl.DefaultDepartmentDAO;
import com.application.model.DepartmentModel;
import com.application.service.DepartmentService;
import com.application.service.impl.DefaultDepartmentService;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.application.controller.CoreConstants.DEPARTMENTS;

public class DepController implements Controller {
    private DepartmentService departmentService = new DefaultDepartmentService();
    private DepartmentsDAO departmentsDAO = new DefaultDepartmentDAO();
    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        DepartmentModel departmentModel = new DepartmentModel();

        if (DEPARTMENTS.equals(request.getServletPath())) {
            List<DepartmentModel> allDepartments = departmentService.getAllDepartments();
            request.setAttribute("departments", allDepartments);
            request.getRequestDispatcher("/WEB-INF/jsp/departments-list.jsp").forward(request, response);
        }

       if ("/department/create".equals(request.getServletPath())) {
           request.getRequestDispatcher("/WEB-INF/jsp/create-edit-department.jsp").forward(request, response);
        }

        if ("/department/edit".equals(request.getServletPath())) {
            int idToEdit = Integer.parseInt(request.getParameter("idToEdit"));
            DepartmentModel departmentById = departmentService.getDepartmentForId(idToEdit);
            if (Objects.nonNull(departmentById)) {
                request.setAttribute("currentDepartment", departmentService.getDepartmentForId(idToEdit));
            }
            request.getRequestDispatcher("/WEB-INF/jsp/create-edit-department.jsp").forward(request, response);
        }
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DepartmentModel departmentModel = new DepartmentModel();


        if (DEPARTMENTS.equals(request.getServletPath())) {
            String idToRemove = request.getParameter("idToRemove");
            List<DepartmentModel> leftDepartments = departmentService.removeDepartment(idToRemove);

            request.setAttribute("departments", leftDepartments);
            request.getRequestDispatcher("/WEB-INF/jsp/departments-list.jsp").forward(request, response);
        }

        if ("/department/create".equals(request.getServletPath())) {
            departmentModel.setName(request.getParameter("name"));
            departmentModel.setAddress(Integer.parseInt(request.getParameter("address")));
            departmentService.createEditDepartment(departmentModel);
            response.sendRedirect(request.getContextPath() + DEPARTMENTS);
        }
        if ("/department/edit".equals(request.getServletPath())) {
            int idToEdit = Integer.parseInt(request.getParameter("id"));
            DepartmentModel departmentForId = departmentsDAO.getDepartmentForId(idToEdit);
            departmentForId.setAddress(Integer.parseInt(request.getParameter("address")));
            departmentForId.setName(request.getParameter("name"));
            departmentsDAO.createEditDepartment(departmentForId);
            response.sendRedirect(request.getContextPath() + DEPARTMENTS);
        }
    }
}
