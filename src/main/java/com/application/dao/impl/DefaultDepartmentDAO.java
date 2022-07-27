package com.application.dao.impl;

import com.application.dao.DepartmentsDAO;
import com.application.model.DepartmentModel;
import com.application.utils.HibernateUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class DefaultDepartmentDAO implements DepartmentsDAO {

    private static final Logger LOGGER = LogManager.getLogger(DefaultDepartmentDAO.class);

    private static SessionFactory sessionFactory;

    public List<DepartmentModel> getAllDepartments() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        transaction = session.beginTransaction();
        List<DepartmentModel> departments = session.createQuery("FROM DepartmentModel").list();
        session.close();
        return departments;
    }

    public void createEditDepartment(DepartmentModel departmentModel) {
        Transaction transaction;
        Session session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        session.saveOrUpdate(departmentModel);
        transaction.commit();
        session.close();
    }

    @Override
    public DepartmentModel getDepartmentForId(int id) {
        DepartmentModel existingDepartment;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from DepartmentModel where id=:depId");
        query.setParameter("depId", id);
        existingDepartment = (DepartmentModel) query.uniqueResult();
        session.close();
        return existingDepartment;
    }


    @Override
    public void removeDepartment(String id) {
        Transaction transaction = null;
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            DepartmentModel department = session.load(DepartmentModel.class, Integer.valueOf(id));
            session.delete(department);
            transaction.commit();
            session.close();

        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }

        }

    }
}
