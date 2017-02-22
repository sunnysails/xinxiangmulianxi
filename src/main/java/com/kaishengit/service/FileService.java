package com.kaishengit.service;


import java.io.InputStream;

/**
 * Created by sunny on 2017/2/20.
 */
public interface FileService {
    String uploadFile(String originalFilename, String contentType, InputStream inputStream);
}
