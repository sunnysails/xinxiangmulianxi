package com.kaishengit.service;

import com.kaishengit.pojo.SkyDrive;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by sunny on 2017/1/22.
 */
public interface SkyDriveService {
    List<SkyDrive> findAllSkyDriveByFid(Integer fid);

    void saveNewDir(SkyDrive skyDrive);

    SkyDrive findSkyDriveById(Integer id);

    void saveNewFile(Integer fid, MultipartFile file);

    InputStream downloadFile(Integer id) throws FileNotFoundException;

    void delById(Integer id);
}
