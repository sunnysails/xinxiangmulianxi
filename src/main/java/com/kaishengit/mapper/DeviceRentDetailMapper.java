package com.kaishengit.mapper;

import com.kaishengit.pojo.DeviceRentDetail;

import java.util.List;

/**
 * Created by sunny on 2017/2/21.
 */
public interface DeviceRentDetailMapper {
    void batchSave(List<DeviceRentDetail> detailList);

    List<DeviceRentDetail> findByRentId(Integer rentId);
}
