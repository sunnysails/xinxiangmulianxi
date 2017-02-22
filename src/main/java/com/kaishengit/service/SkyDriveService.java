package com.kaishengit.service;

import com.kaishengit.pojo.SkyDrive;

import java.util.List;

/**
 * Created by sunny on 2017/1/22.
 */
public interface SkyDriveService {
    List<SkyDrive> findAllSkyDriveByRelationId(Integer relationId);

    Boolean saveNewDic(String newDic, Integer relationId);

    SkyDrive findSkyDriveById(Integer id);
}
