package com.kaishengit.service;

import com.kaishengit.dto.RentDto;
import com.kaishengit.pojo.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

/**
 * Created by sunny on 2017/1/19.
 */
public interface WorkerService {
    List<Worker> findAllWorker();

    void saveNewWork(Worker worker);

    Worker findWorkerById(Integer workerId);

    void updateWorker(Worker worker);

    void delWorkerById(Integer workerId);

    String saveRent(RentDto rentDto);

    WorkerRent findWorkerRentBySerialNumber(String serialNumber);

    List<WorkerRentDetail> findWorkerRentDetailListByRentId(Integer rentId);

    RentDoc findWorkerRentDocById(Integer id);

    void downloadZipFile(WorkerRent workerRent, ZipOutputStream zipOutputStream) throws IOException;

    WorkerRent findWorkerRentById(Integer id);

    Long countOfWorkerRent();

    List<WorkerRent> findDWorkerRentByQueryParam(Map<String, Object> queryParam);

    void changeRentState(Integer id);
}
