package com.application.service;

import com.application.data.EmployeeData;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

public interface EmployeeService {
    List<EmployeeData> getAllEmployees();

    void editEmployee(HttpServletRequest req);

    Map<String, String> convertRequestToMap(HttpServletRequest req);

    List<EmployeeData> convertToEmployeeList(ResultSet resultSet);

    EmployeeData getEmployeeById(String id);
    boolean checkExistingDepartmentId(String id);
    boolean checkExistingEmployeeEmail(String email);
    String getRandomId();

    void createEmployee(HttpServletRequest req);

    EmployeeData convertRequestToEmployee(HttpServletRequest req);
}
