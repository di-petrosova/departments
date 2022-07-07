package com.application.dao;

import java.io.InputStream;
import java.sql.ResultSet;
import java.util.Map;

public interface EmployeesDAO {

    ResultSet getAllEmployees();

    void editEmployee(Map<String, String> employee);

    ResultSet getEmployeeForId(String id);

    boolean checkExistingEmployeeEmail(String email);

    boolean checkExistingEmployeeId(String id);

    void createEmployee(Map<String, String> employee);

    void removeEmployee(String id);

    void editEmployeePhoto(InputStream inputStream, String id);

    byte[] gettingEmployeePhoto();

    InputStream getEmployeePhoto(String id);

}
