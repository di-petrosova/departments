package com.application.service.impl;

import com.application.dao.DepartmentsDAO;
import com.application.dao.impl.DefaultDepartmentDAO;
import com.application.data.DepartmentData;
import com.application.service.DepartmentService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DefaultDepartmentService implements DepartmentService {

    private DepartmentsDAO departmentsDAO = new DefaultDepartmentDAO();
    @Override
    public List<DepartmentData> getAllDepartments() {
        ResultSet resultSet = departmentsDAO.getAllDepartments();
        List<DepartmentData> departmentData = convertToDepartmentList(resultSet);
        return departmentData;
    }

    private List<DepartmentData> convertToDepartmentList(ResultSet resultSet) {
        List<DepartmentData> departmentsList = new ArrayList<>();

        try
        {
            while (resultSet.next())
            {
                DepartmentData departmentData = new DepartmentData();
                departmentData.setId(resultSet.getInt(2));
                departmentData.setName(resultSet.getString(3));
                departmentData.setAddress(resultSet.getInt(4));
                departmentsList.add(departmentData);
            }
        }
        catch (SQLException e)
        {

        }
        return departmentsList;
    }
}
