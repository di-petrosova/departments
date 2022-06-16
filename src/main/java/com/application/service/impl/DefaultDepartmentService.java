package com.application.service.impl;

import com.application.dao.DepartmentsDAO;
import com.application.dao.impl.DefaultDepartmentDAO;
import com.application.data.DepartmentData;
import com.application.service.DepartmentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultDepartmentService implements DepartmentService {
    private static final Logger LOGGER = LogManager.getLogger(DefaultDepartmentDAO.class);
    private DepartmentsDAO departmentsDAO = new DefaultDepartmentDAO();

    @Override
    public List<DepartmentData> getAllDepartments() {
        ResultSet resultSet = departmentsDAO.getAllDepartments();
        List<DepartmentData> departmentData = convertToDepartmentList(resultSet);
        return departmentData;
    }

    private List<DepartmentData> convertToDepartmentList(ResultSet resultSet) {
        List<DepartmentData> departmentsList = new ArrayList<>();

        try {
            while (resultSet.next()) {
                DepartmentData departmentData = new DepartmentData();
                departmentData.setId(resultSet.getInt(2));
                departmentData.setName(resultSet.getString(3));
                departmentData.setAddress(resultSet.getInt(4));
                departmentsList.add(departmentData);
            }
        } catch (SQLException e) {
            LOGGER.error("Select all departments was failed");
        }
        return departmentsList;
    }

    @Override
    public String getRandomPK() {
        String randomPK;
        int intPK;
        do {
            intPK = (int) (Math.random() * 10000);
            randomPK = String.valueOf(intPK);
        }
        while (departmentsDAO.checkExistingDepartmentPK(randomPK));
        return randomPK;
    }

    @Override
    public String getRandomId() {
        String randomId;
        int intId;
        do {
            intId = (int) (Math.random() * 10000);
            randomId = String.valueOf(intId);
        }
        while (departmentsDAO.checkExistingDepartmentId(randomId));
        return randomId;
    }

    private Map<String, String> convertRequestToMap(HttpServletRequest req) {
        Map<String, String> department = new HashMap<>();
        department.put("pk", req.getParameter("pk"));
        department.put("id", req.getParameter("id"));
        department.put("name", req.getParameter("name"));
        department.put("address", req.getParameter("address"));

        return department;
    }

    @Override
    public void createDepartment(HttpServletRequest req) {
        Map<String, String> newDepartment = convertRequestToMap(req);
        newDepartment.put("pk", getRandomPK());
        newDepartment.put("id", getRandomId());
        departmentsDAO.createDepartment(newDepartment);
    }

    @Override
    public void editDepartment(HttpServletRequest req) {
        departmentsDAO.editDepartment(convertRequestToMap(req));
    }

    public DepartmentData getDepartmentById(String idToEdit) {
        ResultSet resultSet = departmentsDAO.getDepartmentForId(idToEdit);
        List<DepartmentData> departmentDataList = convertToDepartmentList(resultSet);
        if (departmentDataList.isEmpty()) {
            return null;
        }

        return departmentDataList.get(0);
    }

    @Override
    public List<DepartmentData> removeDepartment(String id) {
        departmentsDAO.removeDepartment(id);
        return getAllDepartments();
    }
}
