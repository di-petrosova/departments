package com.application.dao;

import java.sql.ResultSet;
import java.util.Map;

public interface EmployeesDAO {
    ResultSet getAllEmployees();

    void editEmployee(Map<String, String> employee);

    ResultSet getEmployeeForId(String id);

    boolean checkExistingEmployeeEmail(String email);

    boolean checkExistingEmployeeId(String id);
}
