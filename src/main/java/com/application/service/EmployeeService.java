package com.application.service;

import com.application.model.EmployeeModel;
import com.application.model.MediaModel;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.util.List;

public interface EmployeeService {
    List<EmployeeModel> getAllEmployees();

    void createEditEmployee(EmployeeModel employeeModel);

    boolean checkExistingEmployeeEmail(String email);

    int getRandomId();

    EmployeeModel convertRequestToEmployee(HttpServletRequest req);

    List<EmployeeModel> removeEmployee(int id);

    boolean checkExistingEmployeeId(int id);

    MediaModel saveUpdatePhoto(FileInputStream fileInputStream, int id, int photoId);

    EmployeeModel getEmployeeForId(int id);
}
