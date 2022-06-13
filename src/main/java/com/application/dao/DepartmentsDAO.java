package com.application.dao;

import java.sql.ResultSet;
import java.util.Map;

public interface DepartmentsDAO {
    ResultSet getAllDepartments();

    void createDepartmentDAO(Map<String, String> department);

    boolean checkExistingDepartmentPK(String pk);
    boolean checkExistingDepartmentId(String id);
}
