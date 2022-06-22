package com.application.dao.impl;

import com.application.dao.EmployeesDAO;
import com.application.dao.factory.DBConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class DefaultEmployeesDAO implements EmployeesDAO {

    private static final Logger LOGGER = LogManager.getLogger(DefaultEmployeesDAO.class);

    @Override
    public ResultSet getAllEmployees() {
        String query = "SELECT * FROM employees";
        ResultSet rs = null;
        try {
            Connection connection = DBConnectionFactory.establishConnection();
            Statement statement = connection.createStatement();
            rs = statement.executeQuery(query);

        } catch (SQLException e) {
            LOGGER.error("Read from database was failed");
            throw new RuntimeException(e);
        }
        return rs;
    }

    @Override
    public void editEmployee(Map<String, String> employee) {
        String query = "UPDATE employees SET id=\'" + employee.get("empId") + "\', firstName = \'" + employee.get("empFirstName") +
                "\', lastName = \'" + employee.get("empLastName") + "\', dateBirth = \'" + employee.get("dateBirth") +
                "\', age = (SELECT TIMESTAMPDIFF(YEAR, \'" + employee.get("dateBirth") + "\', CURDATE()) AS age WHERE id=\'" + employee.get("empId") +
                "\'), email = \'" + employee.get("empEmail") + "\', photo = \'" +
                employee.get("photo") + "\', createdDate = \'" + employee.get("createdDate") + "\', modifiedDate = now(), experience = " +
                employee.get("empExperience") + ", departmentId = \'" + employee.get("departmentId") + "\' " +
                "WHERE id=\'" + employee.get("empId") + "\'";
        try {
            Connection connection = DBConnectionFactory.establishConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);

        } catch (SQLException e) {
            LOGGER.error("Read from database was failed");
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResultSet getEmployeeForId(String id) {
        String query = "SELECT * FROM employees WHERE id=\'" + id + "\'";
        ResultSet rs = null;
        try {
            Connection connection = DBConnectionFactory.establishConnection();
            Statement statement = connection.createStatement();
            rs = statement.executeQuery(query);

        } catch (SQLException e) {
            LOGGER.error("Read from database was failed");
            throw new RuntimeException(e);
        }
        return rs;
    }

    public boolean checkExistingEmployeeEmail(String email) {
        String query = "SELECT COUNT(*) FROM employees WHERE email = \'" + email + "\'";
        ResultSet rs = null;
        int ifExist = 0;

        try {
            Connection connection = DBConnectionFactory.establishConnection();
            Statement statement = connection.createStatement();
            rs = statement.executeQuery(query);
            rs.next();
            ifExist = ((Number) rs.getObject(1)).intValue();

        } catch (SQLException e) {
            LOGGER.error("Read from database was failed", e);

        }
        return ifExist > 0;
    }

    public boolean checkExistingEmployeeId(String id) {
        String query = "SELECT COUNT(*) FROM employees WHERE id = \'" + id + "\'";
        ResultSet rs = null;
        int ifExist = 0;

        try {
            Connection connection = DBConnectionFactory.establishConnection();
            Statement statement = connection.createStatement();
            rs = statement.executeQuery(query);
            rs.next();
            ifExist = ((Number) rs.getObject(1)).intValue();

        } catch (SQLException e) {
            LOGGER.error("Read from database was failed", e);

        }
        return ifExist == 1;
    }

    @Override
    public void createEmployee(Map<String, String> employee) {
        String query = "INSERT employees (id, firstname, lastname, dateBirth, age, email, photo, createdDate, modifiedDate, experience, departmentId)" +
                " VALUES ('" + employee.get("empId") + "\', \'" + employee.get("empFirstName") +
                "\', \'" + employee.get("empLastName") + "\', \'" + employee.get("dateBirth") +
                "\', (SELECT TIMESTAMPDIFF(YEAR, \'" + employee.get("dateBirth") + "\', CURDATE()) AS age), \'" + employee.get("empEmail") + "\', \'" + employee.get("photo") + "\', now(), now(), " +
                employee.get("empExperience") + ", \'" + employee.get("departmentId") + "\');";
        try {
            Connection connection = DBConnectionFactory.establishConnection();
            Statement statement = connection.createStatement();
            statement.execute(query);

        } catch (SQLException e) {
            LOGGER.error("Read from database was failed");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeEmployee(String id) {
        String query = "DELETE FROM employees WHERE id=\'" + id + "\'";
        ResultSet rs = null;
        try{
            Connection connection = DBConnectionFactory.establishConnection();
            Statement statement = connection.createStatement();
            statement.execute(query);
        }
        catch (SQLException e) {
            LOGGER.error("Delete from database was failed", e);
        }
    }
}
