package com.kaishengit.service;


import com.kaishengit.pojo.RentDoc;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipOutputStream;

/**
 * Created by sunny on 2017/2/20.
 */
public interface FileService {
    String uploadFile(String originalFilename, String contentType, InputStream inputStream);

    InputStream downloadFile(Integer id) throws IOException;

    List<RentDoc> findRentDocListByRentId(Integer rentId);

    void getAllFile(Integer id,ZipOutputStream zipOutputStream) throws IOException;
}
