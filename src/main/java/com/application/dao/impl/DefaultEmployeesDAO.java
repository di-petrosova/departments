package com.application.dao.impl;

import com.application.dao.EmployeesDAO;
import com.application.dao.factory.DBConnectionFactory;
import com.application.data.EmployeeData;
import com.application.model.EmployeeModel;
import com.application.utils.HibernateUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class DefaultEmployeesDAO implements EmployeesDAO {

    private static final Logger LOGGER = LogManager.getLogger(DefaultEmployeesDAO.class);

    @Override
    public List<EmployeeModel> getAllEmployees() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        transaction = session.beginTransaction();
        List<EmployeeModel> employees = session.createQuery("FROM EmployeeModel").list();
        session.close();
        return employees;
    }

    @Override
    public void createUpdateEmployee(EmployeeModel employeeModel) {
        Transaction transaction;
        Session session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        session.saveOrUpdate(employeeModel);
        transaction.commit();
        session.close();
    }

    public EmployeeModel checkExistingEmployeeEmail(String email) {
        EmployeeModel existingEmployee;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from EmployeeModel where email=:empEmail");
        query.setParameter("empEmail", email);
        existingEmployee = (EmployeeModel) query.uniqueResult();
        session.close();
        return existingEmployee;
    }

    public EmployeeModel getEmployeeForId(int id) {
        EmployeeModel existingEmployee;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from EmployeeModel where id=:empId");
        query.setParameter("empId", id);
        existingEmployee = (EmployeeModel) query.uniqueResult();
        session.close();
        return existingEmployee;
    }

    @Override
    public InputStream getEmployeePhoto(int id) {
        String sql = "SELECT tempphoto FROM employees WHERE id=?;";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnectionFactory.establishConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getBinaryStream(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    /*@Override
    public void editEmployeePhoto(InputStream inputStream, int id) {
        String sql = "UPDATE employees SET tempphoto=? WHERE id=?;";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBConnectionFactory.establishConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setBlob(1, inputStream);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {   }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {   }
            }
        }
    }*/

   /* public byte[] gettingEmployeePhoto() {
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
    }*/

    @Override
    public void removeEmployee(int id) {
        Transaction transaction = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        EmployeeModel employee = session.load(EmployeeModel.class, id);
        session.delete(employee);
        transaction.commit();
        session.close();
    }


}
