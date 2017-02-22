package com.kaishengit.controller;

import com.google.common.collect.Maps;
import com.kaishengit.pojo.SkyDrive;
import com.kaishengit.service.SkyDriveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by sunny on 2017/1/22.
 */
@Controller
@RequestMapping("/skydrive")
public class SkyDriveController {

    @Autowired
    private SkyDriveService skyDriveService;

    @RequestMapping(method = RequestMethod.GET)
    public String skyDriveList(Model model) {
        int relationId = 0;
        List<SkyDrive> skyDriveList = skyDriveService.findAllSkyDriveByRelationId(relationId);
        model.addAttribute("skyDriveList", skyDriveList);
        return "/skydrive/list";
    }

    @RequestMapping(value = "/{relationId:\\d+}", method = RequestMethod.GET)
    public String skyDriveList(@PathVariable Integer relationId, Model model) {
        SkyDrive skyDrive = skyDriveService.findSkyDriveById(relationId);
        if (skyDrive == null) {
            return "redirect:/skydrive";
        }
        List<SkyDrive> skyDriveList = skyDriveService.findAllSkyDriveByRelationId(relationId);
        model.addAttribute("skyDriveList", skyDriveList);
        return "/skydrive/list";
    }

    @RequestMapping(value = "/newdic", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> newDic(HttpServletRequest request) {
        String newDic = String.valueOf(request.getParameter("inputValue"));
        Integer relationId = Integer.valueOf(request.getParameter("relationId"));

        Boolean date = skyDriveService.saveNewDic(newDic, relationId);

        Map<String, Object> resultMap = Maps.newHashMap();
        if (date) {
            resultMap.put("state", "success");
        } else {
            resultMap.put("state", "error");
        }
        return resultMap;
    }
}