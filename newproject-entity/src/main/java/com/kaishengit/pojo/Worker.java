package com.kaishengit.pojo;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by sunny on 2017/1/19.
 */
@Data
public class Worker implements Serializable {
    private Integer id;
    private String workerName;
    private String workerViewName;
    private Integer workerAllNum;
    private Integer workerNowNum;
    private String workerUnit;
    private Float workerUnitPrice;
    private Timestamp workerCreateTime;
    private Timestamp workerUpdateTime;
}
