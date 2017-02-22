package com.kaishengit.pojo;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by sunny on 2017/1/16.
 */
@Data
public class Device implements Serializable {
    private Integer id;
    private Integer deviceAccount;
    private String deviceName;
    private Integer deviceAllNum;
    private Integer deviceNowNum;
    private String deviceUnit;
    private Float deviceUnitPrice;
    private Timestamp createTime;
    private Timestamp updateTime;
}
