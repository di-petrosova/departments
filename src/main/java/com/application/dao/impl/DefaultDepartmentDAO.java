package com.application.dao.impl;

import com.application.dao.DepartmentsDAO;
import com.application.dao.factory.DBConnectionFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

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

    @Override
    public void createDepartmentDAO(Map<String, String> department) {

        String query = "INSERT newdep.departments (pk, id, name, adress)values (\'" + department.get("pk") + "\',\'" + department.get("id") + "\',\'" + department.get("name") + "\',\'" + department.get("adress") + "\')";

        try {
            Connection connection = DBConnectionFactory.establishConnection();
            Statement statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {

        }
    }

    public boolean checkExistingDepartmentPK(String pk) {
        String query = "SELECT COUNT(*) FROM newdep.departments where PK = \'" + pk +"\' limit 1";
        ResultSet rs = null;
        int ifExist = 0;


        try
        {
            Connection connection = DBConnectionFactory.establishConnection();
            Statement statement = connection.createStatement();
            rs = statement.executeQuery(query);
            rs.next();
            ifExist = ((Number) rs.getObject(1)).intValue();
        }
        catch (SQLException e)
        {


        }
        return ifExist == 1;
    }

    public boolean checkExistingDepartmentId(String id) {
        String query = "SELECT COUNT(*) FROM newdep.departments where id = \'" + id +"\' limit 1";
        ResultSet rs = null;
        int ifExist = 0;

        try
        {
            Connection connection = DBConnectionFactory.establishConnection();
            Statement statement = connection.createStatement();
            rs = statement.executeQuery(query);
            rs.next();
            ifExist = ((Number) rs.getObject(2)).intValue();

        }
        catch (SQLException e)
        {


        }
        return ifExist == 1;
    }
}
