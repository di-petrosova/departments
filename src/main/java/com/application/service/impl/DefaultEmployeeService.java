package com.application.service.impl;

import com.application.dao.EmployeesDAO;
import com.application.dao.impl.DefaultEmployeesDAO;
import com.application.data.EmployeeData;
import com.application.service.EmployeeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultEmployeeService implements EmployeeService {

    private static final Logger LOG = LogManager.getLogger(DefaultEmployeeService.class);
    private EmployeesDAO employeesDAO = new DefaultEmployeesDAO();

    @Override
    public List<EmployeeData> getAllEmployees() {
        ResultSet rs = employeesDAO.getAllEmployees();
        return convertToEmployeeList(rs);
    }

    public List<EmployeeData> convertToEmployeeList(ResultSet resultSet) {
        List<EmployeeData> employeeDataList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                EmployeeData employeeData = new EmployeeData();
                employeeData.setEmpId(resultSet.getInt(1));
                employeeData.setEmpFirstName(resultSet.getString(2));
                employeeData.setEmpLastName(resultSet.getString(3));
                employeeData.setDateBirth(String.valueOf(resultSet.getDate(4)));
                employeeData.setEmpAge(resultSet.getInt(5));
                employeeData.setEmpEmail(resultSet.getString(6));
                employeeData.setPhoto(new File(resultSet.getString(7)));
                employeeData.setCreatedDate(String.valueOf(resultSet.getDate(8)));
                employeeData.setModifiedDate(String.valueOf(resultSet.getDate(9)));
                employeeData.setEmpExperience(resultSet.getBoolean(10));
                employeeData.setDepartmentId(resultSet.getInt(11));
//                employeeData.setTempphoto((Base64.getEncoder().encodeToString(resultSet.getBytes(12))));
                employeeDataList.add(employeeData);
            }
        } catch (SQLException e) {
            LOG.error("Select all employees was failed");
        }

        return employeeDataList;
    }

    @Override
    public EmployeeData getEmployeeById(String id) {
        ResultSet resultSet = employeesDAO.getEmployeeForId(id);
        List<EmployeeData> employeeDataList = convertToEmployeeList(resultSet);
        if (employeeDataList.isEmpty()) {
            return null;
        }
        return employeeDataList.get(0);
    }

    @Override
    public boolean checkExistingDepartmentId(String id) {
        ResultSet allEmployees = employeesDAO.getAllEmployees();
        List<EmployeeData> employeeDataList = new ArrayList<>();
        try {
            while (allEmployees.next()) {
                EmployeeData employeeData = new EmployeeData();
                employeeData.setEmpId(allEmployees.getInt(1));
                employeeDataList.add(employeeData);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < employeeDataList.size(); i++) {
            if (employeeDataList.get(i).equals(id)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Map<String, String> convertRequestToMap(HttpServletRequest req) {
        Map<String, String> employee = new HashMap<>();
        employee.put("empId", req.getParameter("id"));
        employee.put("empFirstName", req.getParameter("empFirstName"));
        employee.put("empLastName", req.getParameter("empLastName"));
        employee.put("dateBirth", req.getParameter("dateBirth"));
        employee.put("empAge", req.getParameter("empAge"));
        employee.put("empEmail", req.getParameter("empEmail"));
        employee.put("photo", req.getParameter("photo"));
        employee.put("createdDate", req.getParameter("createdDate"));
        employee.put("modifiedDate", req.getParameter("modifiedDate"));
        employee.put("empExperience", req.getParameter("empExperience"));
        employee.put("departmentId", req.getParameter("departmentId"));
        employee.put("tempphoto", req.getParameter("tempphoto"));
        return employee;
    }

    @Override
    public void editEmployee(HttpServletRequest req) {
        employeesDAO.editEmployee(convertRequestToMap(req));
    }

    public boolean checkExistingEmployeeEmail(String email) {
        return employeesDAO.checkExistingEmployeeEmail(email);
    }

    public String getRandomId() {
        String randomId;
        int intId;
        do {
            intId = (int) (Math.random() * 10000);
            randomId = String.valueOf(intId);
        }
        while (employeesDAO.checkExistingEmployeeId(randomId));
        return randomId;
    }

    @Override
    public void createEmployee(HttpServletRequest req) {
        Map<String, String> newEmployee = convertRequestToMap(req);
        newEmployee.put("empId", getRandomId());
        employeesDAO.createEmployee(newEmployee);
    }

    @Override
    public EmployeeData convertRequestToEmployee(HttpServletRequest req) {
        EmployeeData employeeData = new EmployeeData();
        try {
            employeeData.setEmpId(Integer.parseInt(req.getParameter("id")));
        } catch (NumberFormatException e) {

        }
        try {
            employeeData.setDepartmentId(Integer.parseInt(req.getParameter("departmentId")));
        } catch (NumberFormatException e) {

        }
        try {
            employeeData.setEmpAge(Integer.parseInt(req.getParameter("empAge")));
        } catch (NumberFormatException e) {

        }
        employeeData.setEmpFirstName(req.getParameter("empFirstName"));
        employeeData.setEmpLastName(req.getParameter("empLastName"));
        employeeData.setDateBirth(req.getParameter("dateBirth"));

        employeeData.setEmpEmail(req.getParameter("empEmail"));
        employeeData.setPhoto(new File(req.getParameter("photo")));
        employeeData.setCreatedDate(req.getParameter("createdDate"));
        employeeData.setModifiedDate(req.getParameter("modifiedDate"));
        employeeData.setEmpExperience(Boolean.parseBoolean(req.getParameter("empExperience")));
        return employeeData;
    }

    @Override
    public List<EmployeeData> removeEmployee(String id) {
        employeesDAO.removeEmployee(id);
        return getAllEmployees();
    }

    private void saveFile(String id) throws IOException {
        InputStream inputStream = employeesDAO.getEmployeePhoto(id);
        String path = "/home/diana/Downloads/1234.txt";
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }

        OutputStream outputStream = new FileOutputStream(file);
        byte[] buf = new byte[8192];
        int length;
        while ((length = inputStream.read(buf)) > 0) {
            outputStream.write(buf, 0, length);
        }
    }

    public String gettingEmployeePhoto() {
        return Base64.getEncoder().encodeToString(employeesDAO.gettingEmployeePhoto());
    }

    public InputStream getEmployeePhoto(String id) {
        return employeesDAO.getEmployeePhoto(id);
    }

    private InputStream gettingPhoto() {
        String resource = getClass().getClassLoader().getResource("").toString();
        String delete = "WEB-INF/classes/";
        String deleteFile = "file:";
        String subPath = resource.replace(delete, "").replace(deleteFile, "");
        String path  = subPath + "images/photo_coming_soon.jpg";
//        File initialFile = new File(path);
        try {
            InputStream targetStream = new FileInputStream(path);
            return targetStream;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public InputStream ifPhotoExists(String id) {
        if (employeesDAO.checkExistingEmployeeId(id)) {
            if (employeesDAO.getEmployeePhoto(id) != null) {
                return this.getEmployeePhoto(id);
            } else {
                return this.gettingPhoto();
            }
        } else {
            return this.gettingPhoto();
        }
    }
}
