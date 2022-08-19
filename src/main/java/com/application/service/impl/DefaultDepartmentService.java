package com.application.service.impl;

import com.application.dao.DepartmentsDAO;
import com.application.dao.impl.DefaultDepartmentDAO;
import com.application.data.EmployeeData;
import com.application.exceptions.ServiceException;
import com.application.model.DepartmentModel;
import com.application.model.EmployeeModel;
import com.application.service.DepartmentService;
import com.application.utils.CustomValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class DefaultDepartmentService implements DepartmentService {
    private static final Logger LOGGER = LogManager.getLogger(DefaultDepartmentDAO.class);

    @Autowired
    private DepartmentsDAO departmentsDAO;

    @Autowired
    private CustomValidator validator;

    @Override
    public List<DepartmentModel> getAllDepartments() {
        List<DepartmentModel> departmentModel = departmentsDAO.getAllDepartments();
        return departmentModel;
    }

    @Override
    public int getRandomPK() {
        int randomPK;
        int intPK;
        do {
            intPK = (int) (Math.random() * 10000);
            randomPK = Integer.valueOf(intPK);
        }
        while (checkExistingDepartmentId(randomPK));
        return randomPK;
    }

    @Override
    public int getRandomId() {
        int randomId;
        int intId;
        do {
            intId = (int) (Math.random() * 10000);
            randomId = Integer.valueOf(intId);
        }
        while (checkExistingDepartmentId(randomId));
        return randomId;
    }

    public boolean checkExistingDepartmentId(int id) {
        DepartmentModel departmentForId = departmentsDAO.getDepartmentForId(id);
        return departmentForId != null;
    }

    public void createEditDepartment(DepartmentModel department) throws ServiceException {
        if (department.getId() == 0) {
            department.setId(getRandomId());
            department.setPk(getRandomPK());
        }
        validator.validate(department);
        departmentsDAO.createEditDepartment(department);
    }

    public DepartmentModel getDepartmentForId(int id) {
        return departmentsDAO.getDepartmentForId(id);
    }


    @Override
    public List<DepartmentModel> removeDepartment(String id) {
        departmentsDAO.removeDepartment(id);
        return getAllDepartments();
    }

    public List<EmployeeData> getEmployeesForDepartmentId(int id) {
        DepartmentModel department = departmentsDAO.getDepartmentForId(id);
        if (department == null) {
            return Collections.emptyList();
        }
        List<EmployeeModel> employeesList = department.getList();
        List<EmployeeData> results = new ArrayList<>();
        for (EmployeeModel employee : employeesList) {
            EmployeeData employeeData = new EmployeeData();
            employeeData.setEmpFirstName(employee.getFirstName());
            employeeData.setEmpLastName(employee.getLastName());
            employeeData.setEmpEmail(employee.getEmail());
            results.add(employeeData);
        }
        return results;
    }
}
