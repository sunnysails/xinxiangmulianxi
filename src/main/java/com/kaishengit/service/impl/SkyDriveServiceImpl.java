package com.kaishengit.service.impl;

import com.google.common.collect.Lists;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.mapper.SkyDriveMapper;
import com.kaishengit.pojo.SkyDrive;
import com.kaishengit.service.SkyDriveService;
import com.kaishengit.shiro.ShiroUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

/**
 * Created by sunny on 2017/1/22.
 */
@Service
public class SkyDriveServiceImpl implements SkyDriveService {

    @Autowired
    private SkyDriveMapper skyDriveMapper;

    @Value("${upload.path}")
    private String savePath;

    @Override
    public List<SkyDrive> findAllSkyDriveByFid(Integer fid) {
        return skyDriveMapper.findAllByFid(fid);
    }

    /**
     * 新建文件夹
     *
     * @param skyDrive
     */
    @Override
    public void saveNewDir(SkyDrive skyDrive) {
        skyDrive.setFileType(SkyDrive.DIR);
        skyDrive.setCreateUser(ShiroUtil.getCurrentUserName());
        skyDrive.setUpdateTime(new Timestamp(DateTime.now().getMillis()));

        skyDriveMapper.save(skyDrive);
    }

    @Override
    public SkyDrive findSkyDriveById(Integer id) {
        return skyDriveMapper.findById(id);
    }

    /**
     * 上传新文件到网盘
     *
     * @param fid
     * @param file
     */
    @Override
    @Transactional
    public void saveNewFile(Integer fid, MultipartFile file) {
        //将文件存到磁盘
        String sourceName = file.getOriginalFilename();
        String fileName = UUID.randomUUID().toString();
        Long size = file.getSize();

        try {
            File saveFile = new File(new File(savePath), fileName);
            FileOutputStream outputStream = new FileOutputStream(saveFile);
            InputStream inputStream = file.getInputStream();
            IOUtils.copy(inputStream, outputStream);
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            throw new ServiceException("文件上传失败", e);
        }

        //保存数据库记录
        SkyDrive skyDrive = new SkyDrive();
        skyDrive.setFid(fid);
        skyDrive.setSourceName(sourceName);
        skyDrive.setFileName(fileName);
        skyDrive.setCreateUser(ShiroUtil.getCurrentUserName());
        skyDrive.setUpdateTime(new Timestamp(DateTime.now().getMillis()));
        skyDrive.setFileType(SkyDrive.FILE);
        skyDrive.setFileSize(FileUtils.byteCountToDisplaySize(size));

        skyDriveMapper.save(skyDrive);
    }

    /**
     * 从网盘单个下载文件
     *
     * @param id
     * @return
     * @throws FileNotFoundException
     */
    @Override
    public InputStream downloadFile(Integer id) throws FileNotFoundException {
        SkyDrive skyDrive = skyDriveMapper.findById(id);
        if (skyDrive == null || SkyDrive.DIR.equals(skyDrive.getFileType())) {
            return null;
        } else {
            FileInputStream inputStream = new FileInputStream(new File(new File(savePath), skyDrive.getFileName()));
            return inputStream;
        }
    }

    @Override
    @Transactional
    public void delById(Integer id) {
        SkyDrive skyDrive = findSkyDriveById(id);
        if (skyDrive != null) {
            if (SkyDrive.FILE.equals(skyDrive.getFileType())) {
                //删除文件
                File file = new File(savePath, skyDrive.getFileName());
                file.delete();
                skyDriveMapper.delete(id);
            } else {
                List<SkyDrive> skyDriveList = skyDriveMapper.findAll();
                List<Integer> delIdList = Lists.newArrayList();
                findDelId(skyDriveList, delIdList, id);
                delIdList.add(id);
                //批量删除
                skyDriveMapper.batchDel(delIdList);
            }
        }
    }

    private void findDelId(List<SkyDrive> skyDriveList, List<Integer> delIdList, Integer id) {
        for (SkyDrive s :
                skyDriveList) {
            if (s.getFid().equals(id)) {
                delIdList.add(s.getId());
                if (SkyDrive.DIR.equals(s.getFileType())) {
                    findDelId(skyDriveList, delIdList, id);
                } else {
                    File file = new File(savePath, s.getFileName());
                    file.delete();
                }
            }
        }
    }
}
