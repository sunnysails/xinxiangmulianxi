package com.kaishengit.mapper;

import com.kaishengit.pojo.Device;

import java.util.List;

/**
 * Created by sunny on 2017/1/16.
 */
public interface DeviceMapper {
    List<Device> findAll();

    void save(Device device);

    int findMaxAccount();

    Device findById(Integer deviceId);

    void update(Device device);

    void delById(Integer deviceId);
}
