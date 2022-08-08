package com.application.service.impl;

import com.application.dao.EmployeesDAO;
import com.application.dao.MediaDAO;
import com.application.exceptions.ServiceException;
import com.application.model.DepartmentModel;
import com.application.model.EmployeeModel;
import com.application.model.MediaModel;
import com.application.service.EmployeeService;
import com.application.utils.CustomValidator;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.List;

@Service
public class DefaultEmployeeService implements EmployeeService {

    private static final Logger LOG = LogManager.getLogger(DefaultEmployeeService.class);

    @Autowired
    private EmployeesDAO employeesDAO;

    @Autowired
    private MediaDAO mediaDAO;

    @Autowired
    private CustomValidator validator;

    @Override
    public List<EmployeeModel> getAllEmployees() {
        return employeesDAO.getAllEmployees();
    }

    @Override
    public boolean checkExistingEmployeeEmail(String email) {
        EmployeeModel employeeModel = employeesDAO.checkExistingEmployeeEmail(email);
        return employeeModel != null;
    }

    @Override
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

    @Override
    public boolean checkExistingEmployeeId(int id) {
        EmployeeModel employeeModel = employeesDAO.getEmployeeForId(id);
        return employeeModel != null;
    }

    @Override
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
        MediaModel media = new MediaModel();
        if (photoId != 0) {
            media.setId(photoId);
        } else {
            media.setId(getRandomId());
        }
        media.setModifiedDate(new Date());
        media.setIdEmp(id);
        mediaDAO.getMediaForId(photoId);
        byte[] bytes = null;
        try {
            bytes = IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        media.setPhoto(bytes);
        mediaDAO.saveUpdatePhoto(media);
        EmployeeModel employee = employeesDAO.getEmployeeForId(id);
        employee.setPhoto(media);
        try {
            createEditEmployee(employee);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return media;
    }
}
