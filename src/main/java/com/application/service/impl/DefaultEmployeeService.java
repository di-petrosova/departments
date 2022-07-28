package com.application.service.impl;

import com.application.dao.EmployeesDAO;
import com.application.dao.MediaDAO;
import com.application.dao.impl.DefaultEmployeesDAO;
import com.application.dao.impl.DefaultMediaDAO;
import com.application.exceptions.ServiceException;
import com.application.model.DepartmentModel;
import com.application.model.EmployeeModel;
import com.application.model.MediaModel;
import com.application.service.EmployeeService;
import com.application.utils.CustomValidator;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.List;

public class DefaultEmployeeService implements EmployeeService {

    private static final Logger LOG = LogManager.getLogger(DefaultEmployeeService.class);
    private EmployeesDAO employeesDAO = new DefaultEmployeesDAO();
    private MediaDAO mediaDAO = new DefaultMediaDAO();
    private CustomValidator validator = new CustomValidator();

    @Override
    public List<EmployeeModel> getAllEmployees() {
        return employeesDAO.getAllEmployees();
    }

    public boolean checkExistingEmployeeEmail(String email) {
        EmployeeModel employeeModel = employeesDAO.checkExistingEmployeeEmail(email);
        return employeeModel != null;
    }

    public void createEditEmployee(EmployeeModel employeeModel) throws ServiceException {
        employeeModel.setAge(getAge(employeeModel));
        validator.validate(employeeModel);
        employeesDAO.createUpdateEmployee(employeeModel);
    }

    private int getAge(EmployeeModel employeeModel) {
        LocalDate dateBirth = null;
        if (!employeeModel.getDateBirth().equals("")) {
            dateBirth = LocalDate.parse(employeeModel.getDateBirth());
        }
        LocalDate curDate = LocalDate.now();
        if ((dateBirth != null)) {
            return Period.between(dateBirth, curDate).getYears();
        } else {
            return 0;
        }
    }

    public int getRandomId() {
        int randomId;
        int intId;
        do {
            intId = (int) (Math.random() * 10000);
            randomId = intId;
        }
        while (checkExistingEmployeeId(randomId));
        return randomId;
    }

    public boolean checkExistingEmployeeId(int id) {
        EmployeeModel employeeModel = employeesDAO.getEmployeeForId(id);
        return employeeModel != null;
    }

    public EmployeeModel getEmployeeForId(int id) {
        return employeesDAO.getEmployeeForId(id);
    }


    @Override
    public EmployeeModel convertRequestToEmployee(HttpServletRequest req) {
        EmployeeModel employeeModel = new EmployeeModel();
        DepartmentModel departmentModel = new DepartmentModel();
        departmentModel.setId(Integer.parseInt(req.getParameter("departmentId")));
        try {
            employeeModel.setId(Integer.parseInt(req.getParameter("id")));
        } catch (NumberFormatException e) {

        }
        try {
            employeeModel.setDepartmentId(departmentModel);
        } catch (NumberFormatException e) {

        }
        try {
            employeeModel.setAge(Integer.parseInt(req.getParameter("empAge")));
        } catch (NumberFormatException e) {

        }
        employeeModel.setFirstName(req.getParameter("firstName"));
        employeeModel.setLastName(req.getParameter("lastName"));
        employeeModel.setDateBirth(req.getParameter("dateBirth"));
        employeeModel.setEmail(req.getParameter("email"));
        employeeModel.setExperience(Boolean.parseBoolean(req.getParameter("empExperience")));
        return employeeModel;
    }

    @Override
    public List<EmployeeModel> removeEmployee(int id) {
        employeesDAO.removeEmployee(id);
        return getAllEmployees();
    }

    @Override
    public MediaModel saveUpdatePhoto(FileInputStream inputStream, int id, int photoId) {
        MediaModel mediaForId = new MediaModel();
        if (photoId != 0) {
            mediaForId.setId(photoId);
        } else {
            mediaForId.setId(getRandomId());
        }
        mediaForId.setModifiedDate(new Date());
        mediaForId.setIdEmp(id);
        mediaDAO.getMediaForId(photoId);
        byte[] bytes = null;
        try {
            bytes = IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaForId.setPhoto(bytes);
        mediaDAO.saveUpdatePhoto(mediaForId);
        return mediaForId;
    }
}
