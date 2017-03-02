package com.kaishengit.controller;

import com.kaishengit.pojo.Device;
import com.kaishengit.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Created by sunny on 2017/1/16.
 */
@Controller
@RequestMapping("/business/device")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @RequestMapping
    public String list(Model model) {
        List<Device> deviceList = deviceService.findAllDevice();
        model.addAttribute("deviceList", deviceList);
        return "/business/device/list";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newDevice() {
        return "/business/device/new";
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String newDevice(Device device, RedirectAttributes redirectAttributes) {
        Integer newAccount = deviceService.saveNewDevice(device);
        redirectAttributes.addFlashAttribute("message", "新设备添加成功！设备号：" + newAccount);
        return "redirect:/business/device";
    }

    @RequestMapping(value = "/{deviceId:\\d+}/edit", method = RequestMethod.GET)
    public String editDevice(@PathVariable Integer deviceId, Model model) {
        Device device = deviceService.findDeviceById(deviceId);
        model.addAttribute("device", device);
        return "/business/device/edit";
    }

    @RequestMapping(value = "/{deviceId:\\d+}/edit", method = RequestMethod.POST)
    public String editDevice(Device device, RedirectAttributes redirectAttributes) {
        deviceService.updateDevice(device);
        redirectAttributes.addFlashAttribute("message","设备信息修改成功！");
        return "redirect:/business/device";
    }
    @RequestMapping(value = "/{deviceId:\\d+}/del", method = RequestMethod.GET)
    public String editDevice(@PathVariable Integer deviceId,RedirectAttributes redirectAttributes){
        deviceService.delDeviceById(deviceId);
        redirectAttributes.addFlashAttribute("message","成功删除设备！");
        return "redirect:/business/device";
    }
}