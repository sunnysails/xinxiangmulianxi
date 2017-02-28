package com.kaishengit.mapper;

import com.kaishengit.pojo.WorkerRent;

import java.util.List;
import java.util.Map;

/**
 * Created by sunny on 2017/2/21.
 */
public interface WorkerRentMapper {
    void save(WorkerRent workerRent);

    void update(WorkerRent workerRent);

    WorkerRent findBySerialNumber(String serialNumber);

    WorkerRent findById(Integer id);

    List<WorkerRent> findByQueryParam(Map<String, Object> queryParam);

    Long count();
}
