package com.kaishengit.pojo;

import com.google.common.collect.Lists;
import com.kaishengit.dto.RentDto;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class RentDoc {
    private Integer id;
    private String sourceName;
    private String newName;
    private Timestamp createTime;
    private Integer rentId;


    public List<RentDoc> docList(Integer id,List<RentDto.FileArrayBean> list){
        List<RentDoc> rentDocList = Lists.newArrayList();
        for (RentDto.FileArrayBean bean :
                list) {
            RentDoc rentDoc = new RentDoc();
            rentDoc.setRentId(id);
            rentDoc.setNewName(bean.getNewName());
            rentDoc.setSourceName(bean.getSourceName());

            rentDocList.add(rentDoc);
        }
        return rentDocList;
    }
}
