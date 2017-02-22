package com.kaishengit.service.impl;

import com.kaishengit.mapper.WorkerMapper;
import com.kaishengit.pojo.Worker;
import com.kaishengit.service.WorkerService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by sunny on 2017/1/19.
 */
@Service
public class WorkerServiceImpl implements WorkerService {
    @Autowired
    private WorkerMapper workerMapper;

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
}
