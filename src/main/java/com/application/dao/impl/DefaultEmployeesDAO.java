package com.application.dao.impl;

import com.application.dao.EmployeesDAO;
import com.application.dao.factory.DBConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class DefaultEmployeesDAO implements EmployeesDAO {

    private static final Logger LOGGER = LogManager.getLogger(DefaultEmployeesDAO.class);

    @Override
    public ResultSet getAllEmployees() {
        String query = "SELECT * FROM employees";
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            connection = DBConnectionFactory.establishConnection();
            statement = connection.createStatement();
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
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            connection = DBConnectionFactory.establishConnection();
            statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            LOGGER.error("Delete from database was failed", e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) { /* Ignored */}
            }
        }
    }

    @Override
    public void editEmployeePhoto(InputStream inputStream, String id) {
        String sql = "UPDATE employees SET tempphoto=? WHERE id=?;";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBConnectionFactory.establishConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setBlob(1, inputStream);
            preparedStatement.setString(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) { /* Ignored */}
            }
        }
    }

    @Override
    public InputStream getEmployeePhoto(String id) {
        String sql = "SELECT tempphoto FROM employees WHERE id=?;";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnectionFactory.establishConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getBinaryStream(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) { /* Ignored */}
            }
        }
    }

    public byte[] gettingEmployeePhoto() {
        String query = "SELECT tempphoto FROM employees";
        ResultSet rs = null;
        try {
            Connection connection = DBConnectionFactory.establishConnection();
            Statement statement = connection.createStatement();
            rs = statement.executeQuery(query);
            rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            byte[] imgData = rs.getBytes("tempphoto");
            return imgData;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //return Base64.getEncoder().encodeToString();
    }
}
