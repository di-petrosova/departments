package com.application.service;

import com.application.data.DepartmentData;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface DepartmentService {
    List<DepartmentData> getAllDepartments();
    void createDepartment(HttpServletRequest req);
    void editDepartment(HttpServletRequest req);
    String getRandomPK();
    String getRandomId();
    DepartmentData getDepartmentById(String id);
}
