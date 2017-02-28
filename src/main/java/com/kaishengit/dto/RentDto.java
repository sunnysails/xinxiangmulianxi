package com.kaishengit.dto;

import lombok.Data;

import java.util.List;

/**
 * Created by sunny on 2017/2/20.
 */
@Data
public class RentDto {

    /**
     * deviceArray : [{"id":"3","name":"10米钢管","unit":"根","price":"2","num":"78","total":156},{"id":"1","name":"挖掘机（大）","unit":"辆","price":"500","num":"4","total":2000}]
     * fileArray : [{"sourceName":"20160428215421916.png","newName":"e8c77f9c-d7d4-402f-abc8-dc4ff040f18b.png"},{"sourceName":"头像.jpg","newName":"38d2489e-8adc-44a9-a0c0-78fd0e85fa70.jpg"},{"sourceName":"web.xml","newName":"721346c0-dcd9-4bbc-8b4f-7e651fb790e8.xml"}]
     * companyName : 华为
     * companyTel : 11111
     * linkManCard : 3726476247654
     * address : 事实上
     * companyFax : 64782346784-
     * rentDate : 2017-02-20
     * backDate : 2017-02-28
     */

    private String companyName;
    private String companyTel;
    private String linkMan;
    private String linkManCard;
    private String address;
    private String companyFax;
    private String rentDate;
    private String backDate;
    private List<DeviceArrayBean> deviceArray;
    private List<FileArrayBean> fileArray;

    @Data
    public static class DeviceArrayBean {
        /**
         * id : 3
         * name : 10米钢管
         * unit : 根
         * price : 2
         * num : 78
         * total : 156
         */

        private Integer id;
        private String name;
        private String unit;
        private Float price;
        private Integer num;
        private Float total;
    }

    @Data
    public static class FileArrayBean {
        /**
         * sourceName : 20160428215421916.png
         * newName : e8c77f9c-d7d4-402f-abc8-dc4ff040f18b.png
         */

        private String sourceName;
        private String newName;
    }
}
