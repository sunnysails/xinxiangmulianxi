package com.kaishengit.controller;

import com.google.common.collect.Maps;
import com.kaishengit.dto.AjaxResult;
import com.kaishengit.exception.NotFoundException;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.pojo.SkyDrive;
import com.kaishengit.service.SkyDriveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by sunny on 2017/1/22.
 */
@Controller
@RequestMapping("/skydrive")
public class SkyDriveController {

    @Autowired
    private SkyDriveService skyDriveService;

    @RequestMapping(method = RequestMethod.GET)
    public String skyDriveList(@RequestParam(required = false, defaultValue = "0") Integer path, Model model) {
        List<SkyDrive> skyDriveList = skyDriveService.findAllSkyDriveByFid(path);
        model.addAttribute("skyDriveList", skyDriveList);
        model.addAttribute("fid", path);
        return "/skydrive/list";
    }

    @RequestMapping(value = "/newdir", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult newDic(SkyDrive skyDrive) {
        skyDriveService.saveNewDir(skyDrive);
        return new AjaxResult(AjaxResult.SUCCESS);
    }

    @PostMapping("/upload")
    @ResponseBody
    public AjaxResult saveFile(Integer fid, MultipartFile file) {
        try {
            skyDriveService.saveNewFile(fid, file);
            return new AjaxResult(AjaxResult.SUCCESS);
        } catch (ServiceException e) {
            return new AjaxResult(e.getMessage());
        }
    }

    /**
     * 下载文件
     * @param id
     * @return
     */
    @GetMapping("/download")
    @ResponseBody
    public ResponseEntity<InputStreamSource> downloadFile(Integer id) {
        try {
            InputStream inputStream = skyDriveService.downloadFile(id);
            SkyDrive skyDrive = skyDriveService.findSkyDriveById(id);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachement", skyDrive.getSourceName(), Charset.forName("UTF-8"));

            return new ResponseEntity<>(new InputStreamResource(inputStream), headers, HttpStatus.OK);
        } catch (IOException e) {
            throw new NotFoundException();
        }
    }
    @GetMapping("/del/{id:\\d+}")
    @ResponseBody
    public AjaxResult del(@PathVariable Integer id){
        skyDriveService.delById(id);
        return new AjaxResult(AjaxResult.SUCCESS);
    }
}