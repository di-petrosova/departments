package com.application.dao.impl;

import com.application.dao.EmployeesDAO;
import com.application.dao.factory.DBConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
}
