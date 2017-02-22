package com.kaishengit.service.impl;

import com.google.common.collect.Lists;
import com.kaishengit.dto.DeviceRentDto;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.mapper.DeviceMapper;
import com.kaishengit.mapper.DeviceRentDetailMapper;
import com.kaishengit.mapper.DeviceRentMapper;
import com.kaishengit.mapper.RentDocMapper;
import com.kaishengit.pojo.Device;
import com.kaishengit.pojo.DeviceRent;
import com.kaishengit.pojo.DeviceRentDetail;
import com.kaishengit.pojo.RentDoc;
import com.kaishengit.service.DeviceService;
import com.kaishengit.shiro.ShiroUtil;
import com.kaishengit.util.SerialNumberUtil;
import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by sunny on 2017/1/16.
 */
@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceMapper deviceMapper;
    @Autowired
    private DeviceRentMapper rentMapper;
    @Autowired
    private DeviceRentDetailMapper detailMapper;
    @Autowired
    private RentDocMapper rentDocMapper;
    @Value("${upload.path}")
    private String fileSavePath;

    /**
     * 获取当前时间到天
     *
     * @return YYYY-MM-DD
     */
    private Timestamp getNowTime() {
        return new Timestamp(new DateTime().getMillis());
    }

    @Override
    public List<Device> findAllDevice() {
        return deviceMapper.findAll();
    }

    @Override
    @Transactional
    public Integer saveNewDevice(Device device) {
        Integer newAccount = deviceMapper.findMaxAccount() + 1;
        device.setDeviceAccount(newAccount);
        device.setDeviceNowNum(device.getDeviceAllNum());
        device.setUpdateTime(getNowTime());
        deviceMapper.save(device);
        return newAccount;
    }

    @Override
    public Device findDeviceById(Integer deviceId) {
        return deviceMapper.findById(deviceId);
    }

    @Override
    @Transactional
    public void updateDevice(Device device) {
        device.setUpdateTime(getNowTime());
        deviceMapper.update(device);
    }

    @Override
    public void delDeviceById(Integer deviceId) {
        deviceMapper.delById(deviceId);
        //TODO 外键约束关系添加后需更改。
    }

    @Override
    @Transactional
    public String saveRent(DeviceRentDto deviceRentDto) {
        //1.保存合同
        DeviceRent deviceRent = new DeviceRent();
        deviceRent.setAddress(deviceRentDto.getAddress());
        deviceRent.setBackDate(deviceRentDto.getBackDate());
        deviceRent.setLinkManCard(deviceRentDto.getLinkManCard());
        deviceRent.setCompanyName(deviceRentDto.getCompanyName());
        deviceRent.setCreateUser(ShiroUtil.getCurrentUserName());
        deviceRent.setCompanyFax(deviceRentDto.getCompanyFax());
        deviceRent.setLastCost(0F);
        deviceRent.setCompanyTel(deviceRentDto.getCompanyTel());
        deviceRent.setLinkMan(deviceRentDto.getLinkMan());
        deviceRent.setPreCost(0F);
        deviceRent.setTotalPrice(0F);
        deviceRent.setRentDate(DateTime.now().toString("yyyy-MM-dd"));
//        deviceRent.setTotalDay(DateUtil.getDiff(DateTime.now().toString("yyyy-MM-dd"),deviceRentDto.getBackDate()));
        deviceRent.setTotalDay(Days.daysBetween(DateTime.now(), new DateTime(deviceRentDto.getBackDate())).getDays() + 1);
        deviceRent.setSerialNumber(SerialNumberUtil.getSerialNumber());

        rentMapper.save(deviceRent);

        //2.保存合同详情
        List<DeviceRentDto.DeviceArrayBean> deviceArrayBeanList = deviceRentDto.getDeviceArray();
        List<DeviceRentDetail> detailList = Lists.newArrayList();
        float total = 0F;
        for (DeviceRentDto.DeviceArrayBean bean :
                deviceArrayBeanList) {
            //查询当前设备库存是否足够,同时更新现有库存数量
            Device device = deviceMapper.findById(bean.getId());
            if (device.getDeviceNowNum() < bean.getNum()) {
                throw new ServiceException(device.getDeviceName() + "库存不足");
            } else {
                device.setDeviceNowNum((device.getDeviceNowNum() - bean.getNum()));
                deviceMapper.update(device);
            }

            DeviceRentDetail detail = new DeviceRentDetail();
            detail.setDeviceName(bean.getName());
            detail.setTotalPrice(device.getDeviceUnitPrice() * bean.getNum());
            detail.setDeviceUnit(bean.getUnit());
            detail.setDeviceUnitPrice(device.getDeviceUnitPrice());
            detail.setRentNum(bean.getNum());
            detail.setRentId(deviceRent.getId());

            detailList.add(detail);
            total += detail.getTotalPrice();
        }
        if (!detailList.isEmpty()) {
            detailMapper.batchSave(detailList);
        }
        //计算合同总价及预付款，尾款
        total = total * deviceRent.getTotalDay();
        float preCost = total * 0.3F;
        float lastCost = total - preCost;
        deviceRent.setTotalPrice(total);
        deviceRent.setPreCost(preCost);
        deviceRent.setLastCost(lastCost);
        rentMapper.update(deviceRent);

        //3.保存文件
        List<DeviceRentDto.FileArrayBean> fileArrayBeanList = deviceRentDto.getFileArray();
        List<RentDoc> rentDocList = Lists.newArrayList();
        for (DeviceRentDto.FileArrayBean bean :
                fileArrayBeanList) {
            RentDoc rentDoc = new RentDoc();
            rentDoc.setRentId(deviceRent.getId());
            rentDoc.setNewName(bean.getNewName());
            rentDoc.setSourceName(bean.getSourceName());

            rentDocList.add(rentDoc);
        }
        if (!rentDocList.isEmpty()) {
            rentDocMapper.batchSave(rentDocList);
        }
        //4.写入财务流水
        // TODO: 2017/2/21 财务流水待添加

        return deviceRent.getSerialNumber();
    }

    /**
     * 根据序列号查询
     *
     * @param serialNumber
     * @return
     */
    @Override
    public DeviceRent findDeviceRentBySerialNumber(String serialNumber) {
        return rentMapper.findBySerialNumber(serialNumber);
    }

    /**
     * 根据设备租赁合同ID查询详情列表
     *
     * @param rentId
     * @return
     */
    @Override
    public List<DeviceRentDetail> findDeviceRentDetailListByRentId(Integer rentId) {
        return detailMapper.findByRentId(rentId);
    }

    @Override
    public List<RentDoc> findDeviceRentDocListByRentId(Integer rentId) {
        return rentDocMapper.findByRentId(rentId);
    }

    @Override
    public RentDoc findDeviceRentDocById(Integer id) {
        return rentDocMapper.findById(id);
    }

    /**
     * 下载合同相关
     * @param id
     * @return
     * @throws IOException
     */
    @Override
    public InputStream downloadFile(Integer id) throws IOException {
        RentDoc rentDoc = rentDocMapper.findById(id);
        if (rentDoc == null) {
            return null;
        } else {
            File file = new File(new File(fileSavePath), rentDoc.getNewName());
            if (file.exists()) {
                return new FileInputStream(file);
            } else {
                return null;
            }
        }
    }

    @Override
    public DeviceRent findDeviceRentById(Integer id) {
        return rentMapper.findById(id);
    }

    @Override
    public void downloadZipFile(DeviceRent deviceRent, ZipOutputStream zipOutputStream) throws IOException {
        //查找合同有多少个合同附件
        List<RentDoc> rentDocList = findDeviceRentDocListByRentId(deviceRent.getId());
        for (RentDoc doc :
                rentDocList) {
            ZipEntry zipEntry = new ZipEntry(doc.getSourceName());
            zipOutputStream.putNextEntry(zipEntry);

            InputStream inputStream = downloadFile(doc.getId());
            IOUtils.copy(inputStream,zipOutputStream);
            inputStream.close();
        }
        zipOutputStream.closeEntry();
        zipOutputStream.flush();
        zipOutputStream.close();
    }

    @Override
    public List<DeviceRent> findDeviceRentByQueryParam(Map<String, Object> queryParam) {
        return rentMapper.findByQueryParam(queryParam);
    }

    @Override
    public Long countOfDeviceRent() {
        return rentMapper.count();
    }

    @Override
    public void changeRentState(Integer id) {
        //1.将合同修改为已完成
        DeviceRent deviceRent = findDeviceRentById(id);
        deviceRent.setState(DeviceRent.FINISHED);
        rentMapper.update(deviceRent);
        //TODO 财务模块
    }
}
