package com.application.controller;

import com.application.dao.EmployeesDAO;
import com.application.dao.MediaDAO;
import com.application.dao.impl.DefaultEmployeesDAO;
import com.application.dao.impl.DefaultMediaDAO;
import com.application.exceptions.ServiceException;
import com.application.model.DepartmentModel;
import com.application.model.EmployeeModel;
import com.application.model.MediaModel;
import com.application.service.DepartmentService;
import com.application.service.EmployeeService;
import com.application.service.MediaService;
import com.application.service.impl.DefaultDepartmentService;
import com.application.service.impl.DefaultEmployeeService;
import com.application.service.impl.DefaultMediaService;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Part;

import static com.application.controller.CoreConstants.EMPLOYEES;

public class EmpController implements Controller {

    private EmployeeService employeeService = new DefaultEmployeeService();
    private EmployeesDAO employeesDAO = new DefaultEmployeesDAO();
    private DepartmentService departmentService = new DefaultDepartmentService();
    private MediaDAO mediaModelDAO = new DefaultMediaDAO();
    private MediaService mediaService = new DefaultMediaService();
    List<DepartmentModel> allDepartments = departmentService.getAllDepartments();

    private static final String SAVE_DIR = "uploadFiles";

    @Override
    public void processGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getServletPath().substring(req.getServletPath().lastIndexOf('/') + 1);

        if (EMPLOYEES.equals(req.getServletPath())) {
            List<EmployeeModel> allEmployees = employeeService.getAllEmployees();
            List<DepartmentModel> allDepartments = departmentService.getAllDepartments();
            req.setAttribute("employees", allEmployees);
            req.setAttribute("departments", allDepartments);
            req.getRequestDispatcher("/WEB-INF/jsp/employee-list.jsp").forward(req, resp);
        }
        if ("/employee/edit".equals(req.getServletPath())) {
            int idToEdit = Integer.valueOf(req.getParameter("idToEdit"));
            EmployeeModel currentEmployee = employeesDAO.getEmployeeForId(idToEdit);
            req.setAttribute("departments", allDepartments);
            req.setAttribute("currentEmployee", currentEmployee);
            req.getRequestDispatcher("/WEB-INF/jsp/create-edit-employee.jsp").forward(req, resp);
        }
        if ("/employee/create".equals(req.getServletPath())) {
            req.setAttribute("departments", allDepartments);
//            req.setAttribute("currentEmployee", currentEmployee);
            req.getRequestDispatcher("/WEB-INF/jsp/create-edit-employee.jsp").forward(req, resp);
        }
        if (("/employee/photo/" + idStr).equals(req.getServletPath())) {
            int photoName = Integer.parseInt(idStr.substring(0, idStr.lastIndexOf('.')));

            ServletOutputStream outputStream = resp.getOutputStream();
            EmployeeModel employee = employeeService.getEmployeeForId(photoName);
            if (employee != null && employee.getPhoto() != null && employee.getPhoto().getPhoto() != null) {
                MediaModel photo = employee.getPhoto();
                outputStream.write(photo.getPhoto());
            } else {
                outputStream.write(mediaService.getDefaultImage());
            }
            //outputStream.flush();
        }
    }

    @Override
    public void processPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServiceException {

        if (EMPLOYEES.equals(req.getServletPath())) {
            int idToRemove = Integer.parseInt(req.getParameter("idToRemove"));
            List<EmployeeModel> leftEmployees = employeeService.removeEmployee(idToRemove);
            req.setAttribute("employees", leftEmployees);
            req.getRequestDispatcher("/WEB-INF/jsp/employee-list.jsp").forward(req, resp);
        }

        if ("/employee/create".equals(req.getServletPath())) {
            String email = req.getParameter("email");
            EmployeeModel employeeModel = new EmployeeModel();
            DepartmentModel departmentModel = new DepartmentModel();
            if (employeeService.checkExistingEmployeeEmail(email)) {
                req.setAttribute("departments", allDepartments);
                req.setAttribute("wrongEmail", email);
                req.setAttribute("createdEmployee", employeeService.convertRequestToEmployee(req));
                req.getRequestDispatcher("/WEB-INF/jsp/create-edit-employee.jsp").forward(req, resp);
            } else {
                int id = employeeService.getRandomId();
                req.setAttribute("employeeId", id);
                departmentModel.setId(Integer.parseInt(req.getParameter("departmentId")));
                employeeModel.setId(id);
                employeeModel.setFirstName(req.getParameter("firstName"));
                employeeModel.setLastName(req.getParameter("lastName"));
                employeeModel.setDateBirth(req.getParameter("dateBirth"));
                employeeModel.setEmail(req.getParameter("email"));
                employeeModel.setCreatedDate(new Date());
                employeeModel.setModifiedDate(new Date());
                employeeModel.setExperience(Boolean.parseBoolean(req.getParameter("empExperience")));
                employeeModel.setDepartmentId(departmentModel);
                try {
                    employeeService.createEditEmployee(employeeModel);
                    resp.sendRedirect(req.getContextPath() + EMPLOYEES);
                } catch (ServiceException e) {
                    req.setAttribute("message", e.getErrors());
                    req.setAttribute("departments", allDepartments);
                    req.setAttribute("createdEmployee", employeeService.convertRequestToEmployee(req));
                    req.getRequestDispatcher("/WEB-INF/jsp/create-edit-employee.jsp").forward(req, resp);
                }

            }
        }

        if ("/employee/edit-photo".equals(req.getServletPath())) {
            int idToEdit = Integer.parseInt(req.getParameter("id"));

            int photoId;
            if (mediaModelDAO.getMediaForIdEmp(idToEdit) != null) {
                photoId = mediaModelDAO.getMediaForIdEmp(idToEdit).getId();
            } else {
                photoId = 0;
            }
            Part filePart = req.getPart("photo");
            String fileName = filePart.getSubmittedFileName();
            List<MediaModel> allMediaModels = mediaModelDAO.getAllMedias();
            req.setAttribute("employees", allMediaModels);

            String appPath = req.getServletContext().getRealPath("");
            String savePath = appPath + SAVE_DIR;
            File fileSaveDir = new File(savePath);

            if (!fileSaveDir.exists()) {
                fileSaveDir.mkdir();
            }
            String filePath = "/home/diana/Downloads/" + fileName;
            for (Part part : req.getParts()) {
                part.write(filePath);
            }

//            ==========================================

            File savedFile = new File(filePath);
            FileInputStream fileInputStream = new FileInputStream(savedFile);

            employeeService.saveUpdatePhoto(fileInputStream, idToEdit, photoId);
            resp.sendRedirect(req.getContextPath() + "/employee/edit?idToEdit=" + idToEdit);

        }

        if ("/employee/edit".equals(req.getServletPath())) {
            EmployeeModel employeeModel = new EmployeeModel();
            int idToEdit = Integer.parseInt(req.getParameter("id"));
            DepartmentModel departmentModel = departmentService.getDepartmentForId(Integer.parseInt(req.getParameter("departmentId")));
            List<MediaModel> allMediaModels = mediaModelDAO.getAllMedias();
            req.setAttribute("employees", allMediaModels);

            MediaModel mediaModel = mediaModelDAO.getMediaForIdEmp(idToEdit);

            MediaModel currentMedia = mediaModelDAO.getMediaForIdEmp(idToEdit);

            EmployeeModel currentEmployee = employeesDAO.getEmployeeForId(idToEdit);
            String email = req.getParameter("empEmail");

            req.setAttribute("currentMedia", currentMedia);

            EmployeeModel employeeById = employeesDAO.getEmployeeForId(idToEdit);
            if (employeeService.checkExistingEmployeeEmail(email)) {
                req.setAttribute("departments", allDepartments);
                req.setAttribute("wrongEmail", email);
                req.setAttribute("currentEmployee", currentEmployee);
                if (email.equals(employeeById.getEmail())) {
                    setEmployeeModel(employeeModel, departmentModel, mediaModel, idToEdit, req);
                    employeeService.createEditEmployee(employeeModel);
                    resp.sendRedirect(req.getContextPath() + EMPLOYEES);
                } else {
                    req.getRequestDispatcher("/WEB-INF/jsp/create-edit-employee.jsp").forward(req, resp);
                }
            } else {
                req.setAttribute("employeeId", employeeService.getRandomId());
                setEmployeeModel(employeeModel, departmentModel, mediaModel, idToEdit, req);
                employeeService.createEditEmployee(employeeModel);
                resp.sendRedirect(req.getContextPath() + EMPLOYEES);
            }
        }
    }

    private void setEmployeeModel(EmployeeModel employeeModel, DepartmentModel departmentModel, MediaModel mediaModel, int idToEdit, HttpServletRequest req) {
        employeeModel.setId(idToEdit);
        employeeModel.setFirstName(req.getParameter("firstName"));
        employeeModel.setLastName(req.getParameter("lastName"));
        employeeModel.setDateBirth(req.getParameter("dateBirth"));
        employeeModel.setEmail(req.getParameter("email"));
        try {
            employeeModel.setCreatedDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(req.getParameter("createdDate")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        employeeModel.setModifiedDate(new Date());
        employeeModel.setPhoto(mediaModel);
        employeeModel.setExperience(Boolean.parseBoolean(req.getParameter("experience")));
        employeeModel.setDepartmentId(departmentModel);
    }
}
