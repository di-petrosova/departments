package com.application.service;

import com.application.model.DepartmentModel;

import java.util.List;

public interface DepartmentService {
    List<DepartmentModel> getAllDepartments();

    int getRandomPK();

    int getRandomId();

    void createEditDepartment(DepartmentModel department);

    DepartmentModel getDepartmentForId(int id);

    List<DepartmentModel> removeDepartment(String id);
}
