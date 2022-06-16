package com.application.dao;

import java.sql.ResultSet;
import java.util.Map;

public interface DepartmentsDAO {
    ResultSet getAllDepartments();

    void createDepartment(Map<String, String> department);

    void editDepartment(Map<String, String> department);

    boolean checkExistingDepartmentPK(String pk);

    boolean checkExistingDepartmentId(String id);

    ResultSet getDepartmentForId(String id);

    void removeDepartment(String id);
}
