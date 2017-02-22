package com.kaishengit.mapper;

import com.kaishengit.pojo.SkyDrive;

import java.util.List;

/**
 * Created by sunny on 2017/1/22.
 */
public interface SkyDriveMapper {
    List<SkyDrive> findAllByRelationId(Integer relationId);

    void saveNewDir(SkyDrive skyDrive);

    SkyDrive findById(Integer relationId);
}
