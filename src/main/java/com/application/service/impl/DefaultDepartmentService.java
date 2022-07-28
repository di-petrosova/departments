package com.application.service.impl;

import com.application.dao.DepartmentsDAO;
import com.application.dao.impl.DefaultDepartmentDAO;
import com.application.exceptions.ServiceException;
import com.application.model.DepartmentModel;
import com.application.service.DepartmentService;
import com.application.utils.CustomValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class DefaultDepartmentService implements DepartmentService {
    private static final Logger LOGGER = LogManager.getLogger(DefaultDepartmentDAO.class);
    private DepartmentsDAO departmentsDAO = new DefaultDepartmentDAO();
    private CustomValidator validator = new CustomValidator();

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
        department.setId(getRandomId());
        department.setPk(getRandomPK());
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
}
