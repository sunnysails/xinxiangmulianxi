package com.kaishengit.service.impl;

import com.google.common.collect.Lists;
import com.kaishengit.dto.RentDto;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.mapper.*;
import com.kaishengit.pojo.*;
import com.kaishengit.service.FileService;
import com.kaishengit.service.WorkerService;
import com.kaishengit.shiro.ShiroUtil;
import com.kaishengit.util.SerialNumberUtil;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;


/**
 * Created by sunny on 2017/1/19.
 */
@Service
public class WorkerServiceImpl implements WorkerService {
    @Autowired
    private WorkerMapper workerMapper;
    @Autowired
    private WorkerRentMapper rentMapper;
    @Autowired
    private WorkerRentDetailMapper detailMapper;
    @Autowired
    private RentDocMapper rentDocMapper;
    @Autowired
    private FinanceMapper financeMapper;
    @Autowired
    private FileService fileService;
    @Value("${upload.path}")
    private String fileSavePath;

    @Override
    public List<Worker> findAllWorker() {
        return workerMapper.findAll();
    }

    /**
     * 获取当前时间到秒
     *
     * @return YYYY-MM-DD hh:mm:ss
     */
    public Timestamp getNowTime() {
        return new Timestamp(new DateTime().getMillis());
    }

    @Override
    public void saveNewWork(Worker worker) {
        worker.setWorkerUpdateTime(getNowTime());
        worker.setWorkerNowNum(worker.getWorkerAllNum());
        workerMapper.saveNew(worker);
    }

    @Override
    public Worker findWorkerById(Integer workerId) {
        return workerMapper.findById(workerId);
    }

    @Override
    public void updateWorker(Worker worker) {
        worker.setWorkerUpdateTime(getNowTime());
        workerMapper.update(worker);
    }

    @Override
    public void delWorkerById(Integer workerId) {
        workerMapper.delById(workerId);
    }

    @Override
    @Transactional
    public String saveRent(RentDto rentDto) {
        //1.保存合同
        WorkerRent rent = new WorkerRent();
        rent.setAddress(rentDto.getAddress());
        rent.setBackDate(rentDto.getBackDate());
        rent.setLinkManCard(rentDto.getLinkManCard());
        rent.setCompanyName(rentDto.getCompanyName());
        rent.setCreateUser(ShiroUtil.getCurrentUserName());
        rent.setCompanyFax(rentDto.getCompanyFax());
        rent.setLastCost(0F);
        rent.setCompanyTel(rentDto.getCompanyTel());
        rent.setLinkMan(rentDto.getLinkMan());
        rent.setPreCost(0F);
        rent.setTotalPrice(0F);
        rent.setRentDate(DateTime.now().toString("yyyy-MM-dd"));
        rent.setTotalDay(Days.daysBetween(DateTime.now(), new DateTime(rentDto.getBackDate())).getDays() + 1);
        rent.setSerialNumber(SerialNumberUtil.getSerialNumber());

        rentMapper.save(rent);

        //2.保存合同详情

        List<RentDto.DeviceArrayBean> deviceArrayBeanList = rentDto.getDeviceArray();
        List<WorkerRentDetail> detailList = Lists.newArrayList();
        float total = 0F;
        for (RentDto.DeviceArrayBean bean :
                deviceArrayBeanList) {
            //查询当前设备库存是否足够,同时更新现有库存数量
            Worker worker = findWorkerById(bean.getId());
            if (worker.getWorkerNowNum() < bean.getNum()) {
                throw new ServiceException(worker.getWorkerViewName() + "人员不足");
            } else {
                worker.setWorkerNowNum((worker.getWorkerNowNum() - bean.getNum()));
                updateWorker(worker);
            }

            WorkerRentDetail detail = new WorkerRentDetail();
            detail.setWorkerName(bean.getName());
            detail.setTotalPrice(worker.getWorkerUnitPrice() * bean.getNum());
            detail.setWorkerPrice(worker.getWorkerUnitPrice());
            detail.setOutNum(bean.getNum());
            detail.setRentId(rent.getId());
            detail.setWorkerId(bean.getId());

            detailList.add(detail);
            total += detail.getTotalPrice();
        }
        if (!detailList.isEmpty()) {
            detailMapper.batchSave(detailList);
        }
        //计算合同总价及预付款，尾款
        total = total * rent.getTotalDay();
        float preCost = total * 0.3F;
        float lastCost = total - preCost;
        rent.setTotalPrice(total);
        rent.setPreCost(preCost);
        rent.setLastCost(lastCost);
        rentMapper.update(rent);
        //3.保存文件
        List<RentDto.FileArrayBean> fileArrayBeanList = rentDto.getFileArray();
        RentDoc rentDoc = new RentDoc();
        List<RentDoc> rentDocList = rentDoc.docList(rent.getId(), fileArrayBeanList);
        if (!rentDocList.isEmpty()) {
            rentDocMapper.batchSave(rentDocList);
        }
        //TODO 将文件存入网盘
        //4.写入财务流水
        Finance finance = new Finance();
        finance.setSerialNumber(SerialNumberUtil.getSerialNumber());
        finance.setType(Finance.FINANCE_IN);
        finance.setMoney(rent.getPreCost());
        finance.setState(Finance.FINANCE_UNFINISH);
        finance.setModel(Finance.FINANCE_RENT);
        finance.setFinanceName(rent.getCompanyName() + Finance.FINANCE_OUT_PRE);
        finance.setCreateDate(DateTime.now().toString("yyyy-MM-dd"));
        finance.setCreateUser(ShiroUtil.getCurrentUserName());
        finance.setRentSerial(rent.getSerialNumber());

        financeMapper.save(finance);

        return rent.getSerialNumber();
    }

    @Override
    public WorkerRent findWorkerRentBySerialNumber(String serialNumber) {
        return rentMapper.findBySerialNumber(serialNumber);
    }

    @Override
    public List<WorkerRentDetail> findWorkerRentDetailListByRentId(Integer rentId) {
        return detailMapper.findByRentId(rentId);
    }


    @Override
    public RentDoc findWorkerRentDocById(Integer id) {
        return null;
    }

    @Override
    public void downloadZipFile(WorkerRent workerRent, ZipOutputStream zipOutputStream) throws IOException {
        //查找合同有多少个合同附件
        fileService.getAllFile(workerRent.getId(), zipOutputStream);
    }

    @Override
    public WorkerRent findWorkerRentById(Integer id) {
        return rentMapper.findById(id);
    }

    @Override
    public Long countOfWorkerRent() {
        return rentMapper.count();
    }

    @Override
    public List<WorkerRent> findDWorkerRentByQueryParam(Map<String, Object> queryParam) {
        return rentMapper.findByQueryParam(queryParam);
    }

    @Override
    public void changeRentState(Integer id) {
        //1.将合同修改为已完成
        WorkerRent workerRent = findWorkerRentById(id);
        workerRent.setState(WorkerRent.FINISHED);
        rentMapper.update(workerRent);
        //2.更新设备数量
        List<WorkerRentDetail> detailList = detailMapper.findByRentId(workerRent.getId());
        for (WorkerRentDetail detail :
                detailList) {
            Worker worker = workerMapper.findById(detail.getWorkerId());
            worker.setWorkerNowNum(worker.getWorkerNowNum() + detail.getOutNum());
            workerMapper.update(worker);
        }

        //3。写入财务模块
        Finance finance = new Finance();
        finance.setSerialNumber(SerialNumberUtil.getSerialNumber());
        finance.setType(Finance.FINANCE_IN);
        finance.setMoney(workerRent.getLastCost());
        finance.setState(Finance.FINANCE_UNFINISH);
        finance.setModel(Finance.FINANCE_W_OUT);
        finance.setFinanceName(workerRent.getCompanyName() + Finance.FINANCE_OUT_LAST);
        finance.setCreateDate(DateTime.now().toString("yyyy-MM-dd"));
        finance.setCreateUser(ShiroUtil.getCurrentUserName());
        finance.setRentSerial(workerRent.getSerialNumber());

        financeMapper.save(finance);

    }

}
