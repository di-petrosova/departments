package com.application.service;

import com.application.data.EmployeeData;
import com.application.exceptions.ServiceException;
import com.application.model.DepartmentModel;

import java.util.List;

public interface DepartmentService {
    List<DepartmentModel> getAllDepartments();

    int getRandomPK();

    int getRandomId();

    void createEditDepartment(DepartmentModel department) throws ServiceException;

    DepartmentModel getDepartmentForId(int id);

    List<DepartmentModel> removeDepartment(String id);

    List<EmployeeData> getEmployeesForDepartmentId(int id);
}
