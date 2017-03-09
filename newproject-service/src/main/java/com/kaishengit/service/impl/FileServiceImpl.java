package com.kaishengit.service.impl;

import com.kaishengit.mapper.RentDocMapper;
import com.kaishengit.pojo.RentDoc;
import com.kaishengit.service.FileService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by sunny on 2017/2/20.
 */
@Service
public class FileServiceImpl implements FileService {
    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);
    @Autowired
    private RentDocMapper rentDocMapper;
    @Value("${upload.path}")
    private String uploadPath;

    @Override
    public String uploadFile(String originalFilename, String contentType, InputStream inputStream) {
        String newFileName = UUID.randomUUID().toString();
        File file = new File(new File(uploadPath), newFileName);

        try {
            OutputStream outputStream = new FileOutputStream(file);
            IOUtils.copy(inputStream, outputStream);
            outputStream.flush();
            outputStream.close();
            inputStream.close();

            return newFileName;
        } catch (IOException e) {
            logger.error("文件上传异常", e);
            throw new RuntimeException("文件上传异常", e);
        }
    }

    /**
     * 下载合同相关
     *
     * @param id
     * @return
     * @throws IOException
     */
    @Override
    public InputStream downloadFile(Integer id) throws IOException {
        RentDoc rentDoc = rentDocMapper.findById(id);
        if (rentDoc == null) {
            return null;
        } else {
            File file = new File(new File(uploadPath), rentDoc.getNewName());
            if (file.exists()) {
                return new FileInputStream(file);
            } else {
                return null;
            }
        }
    }
    @Override
    public List<RentDoc> findRentDocListByRentId(Integer rentId) {
        return rentDocMapper.findByRentId(rentId);
    }
    @Override
    public void getAllFile(Integer id, ZipOutputStream zipOutputStream) throws IOException {
        //查找合同有多少个合同附件
        List<RentDoc> rentDocList = findRentDocListByRentId(id);
        for (RentDoc doc :
                rentDocList) {
            ZipEntry zipEntry = new ZipEntry(doc.getSourceName());
            zipOutputStream.putNextEntry(zipEntry);

            InputStream inputStream = downloadFile(doc.getId());
            IOUtils.copy(inputStream, zipOutputStream);
            inputStream.close();
        }
        zipOutputStream.closeEntry();
        zipOutputStream.flush();
        zipOutputStream.close();
    }
}
