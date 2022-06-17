package com.application.service.impl;

import com.application.dao.DepartmentsDAO;
import com.application.dao.EmployeesDAO;
import com.application.dao.impl.DefaultDepartmentDAO;
import com.application.dao.impl.DefaultEmployeesDAO;
import com.application.data.EmployeeData;
import com.application.service.EmployeeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DefaultEmployeeService implements EmployeeService {

        private static final Logger LOG = LogManager.getLogger(DefaultEmployeeService.class);
    private EmployeesDAO employeesDAO = new DefaultEmployeesDAO();

    @Override
    public List<EmployeeData> getAllEmployees() {
        ResultSet rs = employeesDAO.getAllEmployees();
        return convertToEmployeeList(rs);
    }

    private List<EmployeeData> convertToEmployeeList(ResultSet resultSet) {
        List<EmployeeData> employeeDataList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                EmployeeData employeeData = new EmployeeData();
                employeeData.setEmpId(resultSet.getInt(1));
                employeeData.setEmpFirstName(resultSet.getString(2));
                employeeData.setEmpLastName(resultSet.getString(3));
                employeeData.setDateBirth(resultSet.getDate(4));
                employeeData.setEmpAge(resultSet.getInt(5));
                employeeData.setEmpEmail(resultSet.getString(6));
                employeeData.setPhoto(new File(resultSet.getString(7)));
                employeeData.setCreatedDate(resultSet.getDate(8));
                employeeData.setModifiedDate(resultSet.getDate(9));
                employeeData.setEmpExperience(resultSet.getBoolean(10));
                employeeData.setDepartmentId(resultSet.getInt(11));
                employeeDataList.add(employeeData);
            }
        } catch (SQLException e) {
            LOG.error("Select all employees was failed");
        }

        return employeeDataList;
    }
}
