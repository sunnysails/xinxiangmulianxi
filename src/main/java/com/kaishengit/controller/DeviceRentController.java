package com.kaishengit.controller;

import com.google.common.collect.Maps;
import com.kaishengit.dto.AjaxResult;
import com.kaishengit.dto.DataTablesResult;
import com.kaishengit.dto.DeviceRentDto;
import com.kaishengit.exception.NotFoundException;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.pojo.Device;
import com.kaishengit.pojo.DeviceRent;
import com.kaishengit.pojo.DeviceRentDetail;
import com.kaishengit.pojo.RentDoc;
import com.kaishengit.service.DeviceService;
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
 * Created by sunny on 2017/2/18.
 */
@Controller
@RequestMapping("/business/rent")
public class DeviceRentController {
    @Autowired
    private DeviceService deviceService;

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newRent(Model model) {
        List<Device> deviceList = deviceService.findAllDevice();
        model.addAttribute("deviceList", deviceList);
        return "/business/rent/new";
    }

    @PostMapping("/new")
    @ResponseBody
    public AjaxResult saveRent(@RequestBody DeviceRentDto deviceRentDto) {
        try {
            String serialNumber = deviceService.saveRent(deviceRentDto);
            return new AjaxResult(AjaxResult.SUCCESS, serialNumber);
        } catch (ServiceException e) {
            return new AjaxResult(e.getMessage());
        }
    }

    @GetMapping("/device.json")
    @ResponseBody
    public AjaxResult deviceJson(Integer id) {
        Device device = deviceService.findDeviceById(id);
        if (device == null) {
            return new AjaxResult("设备不存在");
        } else {
            return new AjaxResult(device);
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
        DeviceRent deviceRent = deviceService.findDeviceRentBySerialNumber(serialNumber);
        if (deviceRent == null) {
            throw new NotFoundException();
        } else {
            //2.查询合同详情列表
            List<DeviceRentDetail> detailList = deviceService.findDeviceRentDetailListByRentId(deviceRent.getId());
            //3.查询合同文件列表
            List<RentDoc> docList = deviceService.findDeviceRentDocListByRentId(deviceRent.getId());

            model.addAttribute("rent", deviceRent);
            model.addAttribute("detailList", detailList);
            model.addAttribute("docList", docList);

            return "business/rent/show";
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
        InputStream inputStream = deviceService.downloadFile(id);
        if (inputStream == null) {
            throw new NotFoundException();
        } else {
            RentDoc rentDoc = deviceService.findDeviceRentDocById(id);
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
        DeviceRent deviceRent = deviceService.findDeviceRentById(id);
        if (deviceRent == null) {
            throw new NotFoundException();
        } else {
            //将文件下载标记为二进制
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM.toString());
            //更改文件下载的名称
            String fileName = deviceRent.getCompanyName() + ".zip";
            fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");

            OutputStream outputStream = response.getOutputStream();
            ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);
            deviceService.downloadZipFile(deviceRent, zipOutputStream);
        }
    }

    @GetMapping
    public String rentList() {
        return "business/rent/list";
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

        List<DeviceRent> deviceRentList = deviceService.findDeviceRentByQueryParam(queryParam);
        Long count = deviceService.countOfDeviceRent();

        return new DataTablesResult(draw, count, count, deviceRentList);
    }

    @PostMapping("/state/change")
    @ResponseBody
    public AjaxResult changeRentState(Integer id) {
        deviceService.changeRentState(id);
        return new AjaxResult(AjaxResult.SUCCESS);
    }
}
