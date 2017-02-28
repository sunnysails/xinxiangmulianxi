package com.kaishengit.pojo;

import lombok.Data;

@Data
public class WorkerRentDetail {

    private Integer id;
    private String workerName;
    private Float workerPrice;
    private Integer outNum;
    private Float totalPrice;
    private Integer rentId;
    private Integer workerId;
}
