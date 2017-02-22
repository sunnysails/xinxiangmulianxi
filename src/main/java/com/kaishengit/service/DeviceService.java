package com.kaishengit.service;

import com.kaishengit.dto.DeviceRentDto;
import com.kaishengit.pojo.Device;
import com.kaishengit.pojo.DeviceRent;
import com.kaishengit.pojo.DeviceRentDetail;
import com.kaishengit.pojo.RentDoc;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

/**
 * Created by sunny on 2017/1/16.
 */
public interface DeviceService {
    List<Device> findAllDevice();

    Integer saveNewDevice(Device device);

    Device findDeviceById(Integer deviceId);

    void updateDevice(Device device);

    void delDeviceById(Integer deviceId);

    String saveRent(DeviceRentDto deviceRentDto);

    DeviceRent findDeviceRentBySerialNumber(String serialNumber);

    List<DeviceRentDetail> findDeviceRentDetailListByRentId(Integer rentId);

    List<RentDoc> findDeviceRentDocListByRentId(Integer rentId);

    RentDoc findDeviceRentDocById(Integer id);

    InputStream downloadFile(Integer id) throws IOException;

    DeviceRent findDeviceRentById(Integer id);

    void downloadZipFile(DeviceRent deviceRent, ZipOutputStream zipOutputStream) throws IOException;

    List<DeviceRent> findDeviceRentByQueryParam(Map<String, Object> queryParam);

    Long countOfDeviceRent();

    void changeRentState(Integer id);
}
