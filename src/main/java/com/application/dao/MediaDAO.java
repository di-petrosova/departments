package com.application.dao;

import com.application.model.MediaModel;

import java.util.List;

public interface MediaDAO {

    List<MediaModel> getAllMedias();

    MediaModel getMediaForId(int id);

    void removeMedia(int id);

    void saveUpdatePhoto(MediaModel mediaModel);

    MediaModel getMediaForIdEmp(int idEmp);
}
