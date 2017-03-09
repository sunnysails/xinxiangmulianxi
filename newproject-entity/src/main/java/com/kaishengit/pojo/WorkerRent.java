package com.kaishengit.pojo;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class WorkerRent {
    public static final Integer FINISHED = 1;
    public static final Integer UNFINISHED = 0;

    private Integer id;
    private String serialNumber;
    private String companyName;
    private String linkMan;
    private String linkManCard;
    private String companyTel;
    private String address;
    private String companyFax;
    private String createUser;
    private String rentDate;
    private String backDate;
    private Integer totalDay;
    private Float totalPrice;
    private Float preCost;
    private Float lastCost;
    private Integer state;
    private Integer isRenew;
    private Timestamp createTime;
}
