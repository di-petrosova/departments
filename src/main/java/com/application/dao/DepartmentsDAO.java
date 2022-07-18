package com.application.dao;

import com.application.model.DepartmentModel;

import java.util.List;

public interface DepartmentsDAO {
    List<DepartmentModel> getAllDepartments();

    void createEditDepartment(DepartmentModel departmentModel);

    DepartmentModel getDepartmentForId(int id);

    void removeDepartment(String id);
}
