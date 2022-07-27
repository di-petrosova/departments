package com.application.dao.impl;

import com.application.dao.EmployeesDAO;
import com.application.model.EmployeeModel;
import com.application.model.MediaModel;
import com.application.utils.HibernateUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
    public MediaModel getMediaForId(int id) {
        MediaModel mediaModel;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from MediaModel where id=:id");
        query.setParameter("id", id);
        mediaModel = (MediaModel) query.uniqueResult();
        session.close();
        return mediaModel;
    }

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
