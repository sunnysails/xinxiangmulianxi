package com.kaishengit.mapper;

import com.kaishengit.pojo.RentDoc;

import java.util.List;

/**
 * Created by sunny on 2017/2/21.
 */
public interface RentDocMapper {
    void batchSave(List<RentDoc> rentDocList);

    List<RentDoc> findByRentId(Integer rentId);

    RentDoc findById(Integer id);

}
