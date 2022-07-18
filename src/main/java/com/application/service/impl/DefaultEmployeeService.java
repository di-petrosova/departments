package com.application.service.impl;

import com.application.dao.EmployeesDAO;
import com.application.dao.impl.DefaultEmployeesDAO;
import com.application.data.EmployeeData;
import com.application.model.EmployeeModel;
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
import java.time.LocalDate;
import java.time.Period;
import java.util.Base64;
import java.util.List;

public class DefaultEmployeeService implements EmployeeService {

    private static final Logger LOG = LogManager.getLogger(DefaultEmployeeService.class);
    private EmployeesDAO employeesDAO = new DefaultEmployeesDAO();

    @Override
    public List<EmployeeModel> getAllEmployees() {
        return employeesDAO.getAllEmployees();
    }

    public boolean checkExistingEmployeeEmail(String email) {
        EmployeeModel employeeModel = employeesDAO.checkExistingEmployeeEmail(email);
        return employeeModel != null;
    }

    public void createEditEmployee(EmployeeModel employeeModel) {
        employeeModel.setAge(getAge(employeeModel));
        employeesDAO.createUpdateEmployee(employeeModel);
    }

    private int getAge(EmployeeModel employeeModel) {
        LocalDate dateBirth = LocalDate.parse(employeeModel.getDateBirth());
        LocalDate curDate = LocalDate.now();
        if ((dateBirth != null)) {
            return Period.between(dateBirth, curDate).getYears();
        } else {
            return 0;
        }
    }

    public int getRandomId() {
        int randomId;
        int intId;
        do {
            intId = (int) (Math.random() * 10000);
            randomId = intId;
        }
        while (checkExistingEmployeeId(randomId));
        return randomId;
    }

    public boolean checkExistingEmployeeId(int id) {
        EmployeeModel employeeModel = employeesDAO.getEmployeeForId(id);
        return employeeModel != null;
    }

/*    @Override
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
    }*/
/*
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
    }*/

 /*    @Override
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
    }*/

    @Override
    public EmployeeModel convertRequestToEmployee(HttpServletRequest req) {
        EmployeeModel employeeModel = new EmployeeModel();
        try {
            employeeModel.setId(Integer.parseInt(req.getParameter("id")));
        } catch (NumberFormatException e) {

        }
        try {
            employeeModel.setDepartmentId(Integer.parseInt(req.getParameter("departmentId")));
        } catch (NumberFormatException e) {

        }
        try {
            employeeModel.setAge(Integer.parseInt(req.getParameter("empAge")));
        } catch (NumberFormatException e) {

        }
        employeeModel.setFirstName(req.getParameter("firstName"));
        employeeModel.setLastName(req.getParameter("lastName"));
        employeeModel.setDateBirth(req.getParameter("dateBirth"));
        employeeModel.setEmail(req.getParameter("email"));
//        employeeData.setPhoto(new File(req.getParameter("photo")));
//        employeeModel.setCreatedDate(req.getParameter("createdDate"));
//        employeeModel.setModifiedDate(req.getParameter("modifiedDate"));
        employeeModel.setExperience(Boolean.parseBoolean(req.getParameter("experience")));
        return employeeModel;
    }

    @Override
    public List<EmployeeModel> removeEmployee(int id) {
        employeesDAO.removeEmployee(id);
        return getAllEmployees();
    }

/*    private void saveFile(int id) throws IOException {
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
        }*/
/*
    }
    public String gettingEmployeePhoto() {
        return Base64.getEncoder().encodeToString(employeesDAO.gettingEmployeePhoto());
    }
*/
/*
    public InputStream getEmployeePhoto(int id) {
        return employeesDAO.getEmployeePhoto(id);
    }*/

/*    private InputStream gettingPhoto() {
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
    }*/
/*    public InputStream ifPhotoExists(int id) {
        if (employeesDAO.getEmployeeForId(id) != null ) {
            if (employeesDAO.getEmployeePhoto(id) != null) {
                return this.getEmployeePhoto(id);
            } else {
                return this.gettingPhoto();
            }
        } else {
            return this.gettingPhoto();
        }
    }*/
}
