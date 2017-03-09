package com.kaishengit.pojo;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by sunny on 2017/1/17.
 */
@Data
public class SkyDrive implements Serializable {
    public static final String DIR = "dir";
    public static final String FILE = "file";

    private Integer id;
    private String fileName;
    private String fileType;
    private String fileSize;
    private Integer fid;
    private String sourceName;
    private Timestamp createTime;
    private String createUser;
    private Timestamp updateTime;
}
