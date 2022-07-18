package com.application.dao;

import com.application.data.EmployeeData;
import com.application.model.EmployeeModel;

import java.io.InputStream;
import java.util.List;

public interface EmployeesDAO {

    List<EmployeeModel> getAllEmployees();

    void createUpdateEmployee(EmployeeModel employeeModel);

    EmployeeModel getEmployeeForId(int id);

    EmployeeModel checkExistingEmployeeEmail(String email);

    void removeEmployee(int id);

//    void editEmployeePhoto(InputStream inputStream, int id);

//    byte[] gettingEmployeePhoto();

    InputStream getEmployeePhoto(int id);

}
