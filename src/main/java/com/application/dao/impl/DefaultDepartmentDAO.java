package com.application.dao.impl;

import com.application.dao.DepartmentsDAO;
import com.application.dao.factory.DBConnectionFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DefaultDepartmentDAO implements DepartmentsDAO {

    @Override
    public ResultSet getAllDepartments() {
        String query = "SELECT * FROM newdep.departments";
        ResultSet rs = null;
        try {
            Connection connection = DBConnectionFactory.establishConnection();
            Statement statement = connection.createStatement();
            rs = statement.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
     return rs;

    }
}
