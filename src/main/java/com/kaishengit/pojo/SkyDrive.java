package com.kaishengit.pojo;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by sunny on 2017/1/17.
 */
@Data
public class SkyDrive implements Serializable {
    public static final String DIC = "dic";

    private Integer id;
    private String fileName;
    private String fileType;
    private Double fileSize;
    private Integer relationId;
    private String storeName;
    private Timestamp createTime;
    private Timestamp updateTime;
}
