package com.application.dao;

import com.application.model.EmployeeModel;
import com.application.model.MediaModel;

import java.util.List;

public interface EmployeesDAO {

    List<EmployeeModel> getAllEmployees();

    void createUpdateEmployee(EmployeeModel employeeModel);

    EmployeeModel getEmployeeForId(int id);

    EmployeeModel checkExistingEmployeeEmail(String email);

    void removeEmployee(int id);

    MediaModel getMediaForId(int id);

}
