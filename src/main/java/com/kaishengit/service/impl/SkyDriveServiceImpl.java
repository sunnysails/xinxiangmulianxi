package com.kaishengit.service.impl;

import com.kaishengit.mapper.SkyDriveMapper;
import com.kaishengit.pojo.SkyDrive;
import com.kaishengit.service.SkyDriveService;
import com.kaishengit.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by sunny on 2017/1/22.
 */
@Service
public class SkyDriveServiceImpl implements SkyDriveService {

    @Autowired
    private SkyDriveMapper skyDriveMapper;

    @Override
    public List<SkyDrive> findAllSkyDriveByRelationId(Integer relationId) {
        return skyDriveMapper.findAllByRelationId(relationId);
    }

    @Override
    @Transactional
    public Boolean saveNewDic(String newDic, Integer relationId) {
        SkyDrive skyDrive = skyDriveMapper.findById(relationId);
        if (skyDrive == null && relationId != 0) {
            return false;
        }
        SkyDrive skyDrive1 = new SkyDrive();
        skyDrive1.setFileName(newDic);
        skyDrive1.setFileType(SkyDrive.DIC);
        skyDrive1.setStoreName(newDic);
        skyDrive1.setRelationId(relationId);
        skyDrive1.setUpdateTime(Timestamp.valueOf(DateUtil.getCurrentTime(DateUtil.PATTERN_STANDARD19H)));
        skyDriveMapper.saveNewDir(skyDrive1);
        if (skyDrive1.getId() != null) {
            return true;
        }
        return false;
    }

    @Override
    public SkyDrive findSkyDriveById(Integer id) {
        return skyDriveMapper.findById(id);
    }
}
