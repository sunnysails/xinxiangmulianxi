package com.kaishengit.controller;

import com.google.common.collect.Maps;
import com.kaishengit.dto.AjaxResult;
import com.kaishengit.dto.DataTablesResult;
import com.kaishengit.dto.RentDto;
import com.kaishengit.exception.NotFoundException;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.pojo.*;
import com.kaishengit.service.FileService;
import com.kaishengit.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

/**
 * Created by sunny on 2017/2/26.
 */
@Controller
@RequestMapping("/business/out")
public class WorkerOutController {
    @Autowired
    private WorkerService workerService;
    @Autowired
    private FileService fileService;

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newRent(Model model) {
        List<Worker> workerList = workerService.findAllWorker();
        model.addAttribute("workerList", workerList);
        return "/business/out/new";
    }

    @GetMapping("/worker.json")
    @ResponseBody
    public AjaxResult deviceJson(Integer id) {
        Worker worker = workerService.findWorkerById(id);
        if (worker == null) {
            return new AjaxResult("工种不存在");
        } else {
            return new AjaxResult(worker);
        }
    }

    @PostMapping("/new")
    @ResponseBody
    public AjaxResult saveRent(@RequestBody RentDto RentDto) {
        try {
            String serialNumber = workerService.saveRent(RentDto);
            return new AjaxResult(AjaxResult.SUCCESS, serialNumber);
        } catch (ServiceException e) {
            return new AjaxResult(e.getMessage());
        }
    }

    /**
     * 根据流水号显示合同详情
     *
     * @param serialNumber
     * @return
     */
    @GetMapping("/{serialNumber:\\d+}")
    public String showDeviceRent(@PathVariable String serialNumber, Model model) {
        //1.查询合同对象
        WorkerRent workerRent = workerService.findWorkerRentBySerialNumber(serialNumber);
        if (workerRent == null) {
            throw new NotFoundException();
        } else {
            //2.查询合同详情列表
            List<WorkerRentDetail> detailList = workerService.findWorkerRentDetailListByRentId(workerRent.getId());
            //3.查询合同文件列表
            List<RentDoc> docList = fileService.findRentDocListByRentId(workerRent.getId());

            model.addAttribute("rent", workerRent);
            model.addAttribute("detailList", detailList);
            model.addAttribute("docList", docList);

            return "business/out/show";
        }
    }

    /**
     * 合同文件下载
     *
     * @param id 合同文件Id
     * @return 合同文件二进制流
     * @throws IOException
     */
    @GetMapping("/doc")
    @ResponseBody
    public ResponseEntity<InputStreamResource> downloadFile(Integer id) throws IOException {
        InputStream inputStream = fileService.downloadFile(id);
        if (inputStream == null) {
            throw new NotFoundException();
        } else {
            RentDoc rentDoc = workerService.findWorkerRentDocById(id);
            String fileName = rentDoc.getSourceName();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", fileName, Charset.forName("UTF-8"));
            return new ResponseEntity<>(new InputStreamResource(inputStream), headers, HttpStatus.OK);
        }
    }

    /**
     * 合同文件打包下载
     *
     * @param id
     * @param response
     * @throws IOException
     */
    @GetMapping("/doc/zip")
    public void downloadZipFile(Integer id, HttpServletResponse response) throws IOException {
        WorkerRent workerRent = workerService.findWorkerRentById(id);
        if (workerRent == null) {
            throw new NotFoundException();
        } else {
            //将文件下载标记为二进制
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM.toString());
            //更改文件下载的名称
            String fileName = workerRent.getCompanyName() + ".zip";
            fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");

            OutputStream outputStream = response.getOutputStream();
            ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);
            workerService.downloadZipFile(workerRent, zipOutputStream);
        }
    }

    @GetMapping
    public String outList() {
        return "business/out/list";
    }

    /**
     * 获取DateTable数据信息
     *
     * @param request
     * @return
     */
    @GetMapping("/load")
    @ResponseBody
    public DataTablesResult load(HttpServletRequest request) {
        String draw = request.getParameter("draw");
        String start = request.getParameter("start");
        String length = request.getParameter("length");

        Map<String, Object> queryParam = Maps.newHashMap();
        queryParam.put("start", start);
        queryParam.put("length", length);

        List<WorkerRent> deviceRentList = workerService.findDWorkerRentByQueryParam(queryParam);
        Long count = workerService.countOfWorkerRent();

        return new DataTablesResult(draw, count, count, deviceRentList);
    }

    @PostMapping("/state/change")
    @ResponseBody
    public AjaxResult changeRentState(Integer id) {
        workerService.changeRentState(id);
        return new AjaxResult(AjaxResult.SUCCESS);
    }
}
