package com.kaishengit.service;

import com.kaishengit.pojo.Worker;

import java.util.List;

/**
 * Created by sunny on 2017/1/19.
 */
public interface WorkerService {
    List<Worker> findAllWorker();

    void saveNewWork(Worker worker);

    Worker findWorkerById(Integer workerId);

    void updateWorker(Worker worker);

    void delWorkerById(Integer workerId);
}
