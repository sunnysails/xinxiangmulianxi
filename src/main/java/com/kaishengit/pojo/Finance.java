package com.kaishengit.pojo;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Finance {
    public static final String FINANCE_IN = "收入";
    public static final String FINANCE_OUT = "支出";
    public static final String FINANCE_FINISH = "完成";
    public static final String FINANCE_UNFINISH = "未完成";
    public static final String FINANCE_RENT = "设备租赁";
    public static final String FINANCE_RENT_PRE = "设备租赁预付款";
    public static final String FINANCE_RENT_LAST = "设备租赁尾款";
    public static final String FINANCE_W_OUT = "劳务外包";
    public static final String FINANCE_OUT_PRE = "劳务外包预付款";
    public static final String FINANCE_OUT_LAST = "劳务外包尾款";

    private Integer id;
    private String serialNumber;
    private String type;
    private Float money;
    private String state;
    private String model;
    private String financeName;
    private String createDate;
    private String createUser;
    private String confirmUser;
    private String confirmDate;
    private String rentSerial;
    private String remark;
    private Timestamp updateTime;
}
