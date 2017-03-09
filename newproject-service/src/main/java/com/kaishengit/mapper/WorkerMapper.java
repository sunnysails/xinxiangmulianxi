package com.kaishengit.mapper;

import com.kaishengit.pojo.Worker;

import java.util.List;

/**
 * Created by sunny on 2017/1/19.
 */
public interface WorkerMapper {
    List<Worker> findAll();

    void saveNew(Worker worker);

    Worker findById(Integer workerId);

    void update(Worker worker);

    void delById(Integer workerId);
}
