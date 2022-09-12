package com.application.service.impl;

import com.application.service.MediaService;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@Service
public class DefaultMediaService implements MediaService {
    @Override
    public byte[] getDefaultImage() {
        String resource = getClass().getClassLoader().getResource("").toString();
        byte[] bytes = null;
        String delete = "WEB-INF/classes/";
        String deleteFile = "file:";
        String subPath = resource.replace(delete, "").replace(deleteFile, "");
        String path = subPath + "images/photo_coming_soon.jpg";

        InputStream targetStream = null;
        try {
            targetStream = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            bytes = IOUtils.toByteArray(targetStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;

    }
}
