package com.kaishengit.mapper;

import com.kaishengit.pojo.WorkerRentDetail;

import java.util.List;

/**
 * Created by sunny on 2017/2/26.
 */
public interface WorkerRentDetailMapper {
    void batchSave(List<WorkerRentDetail> detailList);

    List<WorkerRentDetail> findByRentId(Integer rentId);
}
