package com.kaishengit.mapper;

import com.kaishengit.pojo.DeviceRent;

import java.util.List;
import java.util.Map;

/**
 * Created by sunny on 2017/2/21.
 */
public interface DeviceRentMapper {
    void save(DeviceRent deviceRent);

    void update(DeviceRent deviceRent);

    DeviceRent findBySerialNumber(String serialNumber);

    DeviceRent findById(Integer id);

    List<DeviceRent> findByQueryParam(Map<String, Object> queryParam);

    Long count();
}
