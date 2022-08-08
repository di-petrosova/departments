package com.application.dao.impl;

import com.application.dao.MediaDAO;
import com.application.model.MediaModel;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DefaultMediaDAO implements MediaDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<MediaModel> getAllMedias() {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        transaction = session.beginTransaction();
        List<MediaModel> mediaModels = session.createQuery("FROM MediaModel").list();
        session.close();
        return mediaModels;
    }

    @Override
    public MediaModel getMediaForId(int id) {
        MediaModel mediaModel;
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from MediaModel where id=:mediaId");
        query.setParameter("mediaId", id);
        mediaModel = (MediaModel) query.uniqueResult();
        session.close();
        return mediaModel;
    }

    @Override
    public void removeMedia(int id) {

    }

    @Override
    public void saveUpdatePhoto(MediaModel mediaModel) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(mediaModel);
        transaction.commit();
        session.close();
    }

    @Override
    public MediaModel getMediaForIdEmp(int idEmp) {
        MediaModel mediaModel;
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from MediaModel where idEmp=:idEmp");
        query.setParameter("idEmp", idEmp);
        mediaModel = (MediaModel) query.uniqueResult();
        session.close();
        return mediaModel;
    }
}
