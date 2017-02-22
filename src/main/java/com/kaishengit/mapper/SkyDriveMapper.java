package com.kaishengit.mapper;

import com.kaishengit.pojo.SkyDrive;

import java.util.List;

/**
 * Created by sunny on 2017/1/22.
 */
public interface SkyDriveMapper {
    List<SkyDrive> findAllByFid(Integer fid);

    void save(SkyDrive skyDrive);

    SkyDrive findById(Integer fid);

    List<SkyDrive> findAll();

    void delete(Integer id);

    void batchDel(List<Integer> delIdList);
}
