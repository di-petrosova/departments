package com.application.service;

import com.application.data.EmployeeData;
import com.application.model.EmployeeModel;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.List;

public interface EmployeeService {
    List<EmployeeModel> getAllEmployees();

    void createEditEmployee(EmployeeModel employeeModel);

//   void editEmployee(HttpServletRequest req);
//
//    Map<String, String> convertRequestToMap(HttpServletRequest req);
//
//    List<EmployeeData> convertToEmployeeList(ResultSet resultSet);
//
//    EmployeeData getEmployeeById(String id);
//
//    boolean checkExistingDepartmentId(String id);
//
    boolean checkExistingEmployeeEmail(String email);

    int getRandomId();
//
//    void createEmployee(HttpServletRequest req);

    EmployeeModel convertRequestToEmployee(HttpServletRequest req);

    List<EmployeeModel> removeEmployee(int id);

//    String gettingEmployeePhoto();

//    InputStream getEmployeePhoto(int id);

//    InputStream ifPhotoExists(int id);

    boolean checkExistingEmployeeId(int id);
}
