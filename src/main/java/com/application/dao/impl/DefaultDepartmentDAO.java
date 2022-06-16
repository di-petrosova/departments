package com.application.dao.impl;

import com.application.dao.DepartmentsDAO;
import com.application.dao.factory.DBConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class DefaultDepartmentDAO implements DepartmentsDAO {

    private static final Logger LOGGER = LogManager.getLogger(DefaultDepartmentDAO.class);

    @Override
    public ResultSet getAllDepartments() {
        String query = "SELECT * FROM departments";
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
    public void createDepartment(Map<String, String> department) {

        String query = "INSERT departments (pk, id, name, adress)values (\'" + department.get("pk") + "\',\'" + department.get("id") + "\',\'" + department.get("name") + "\',\'" + department.get("address") + "\')";

        try {
            Connection connection = DBConnectionFactory.establishConnection();
            Statement statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            LOGGER.error("Read from database was failed");
        }
    }

    @Override
    public void editDepartment(Map<String, String> department) {
        String query = "UPDATE departments SET name = \'" + department.get("name") + "\', adress =\'" + department.get("address") + "\'" + " WHERE id='" + department.get("id") + "\'";

        try {
            Connection connection = DBConnectionFactory.establishConnection();
            Statement statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            LOGGER.error("Read from database was failed");
        }
    }

    public boolean checkExistingDepartmentPK(String pk) {
        String query = "SELECT COUNT(*) FROM departments where PK = \'" + pk + "\' limit 1";
        ResultSet rs = null;
        int ifExist = 0;


        try {
            Connection connection = DBConnectionFactory.establishConnection();
            Statement statement = connection.createStatement();
            rs = statement.executeQuery(query);
            rs.next();
            ifExist = ((Number) rs.getObject(1)).intValue();
        } catch (SQLException e) {
            LOGGER.error("Read from database was failed");
        }
        return ifExist == 1;
    }

    public boolean checkExistingDepartmentId(String id) {
        String query = "SELECT COUNT(*) FROM departments where id = \'" + id + "\' limit 1";
        ResultSet rs = null;
        int ifExist = 0;

        try {
            Connection connection = DBConnectionFactory.establishConnection();
            Statement statement = connection.createStatement();
            rs = statement.executeQuery(query);
            rs.next();
            ifExist = ((Number) rs.getObject(2)).intValue();

        } catch (SQLException e) {
            LOGGER.error("Read from database was failed");
        }
        return ifExist == 1;
    }

    @Override
    public ResultSet getDepartmentForId(String id) {
        String query = "SELECT * FROM departments WHERE id=\'" + id + "\'";
        ResultSet rs = null;

        try {
            Connection connection = DBConnectionFactory.establishConnection();
            Statement statement = connection.createStatement();
            rs = statement.executeQuery(query);

        } catch (SQLException e) {
            LOGGER.error("Read from database was failed");
        }
        return rs;
    }
}
