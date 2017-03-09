package com.kaishengit.controller;

import com.kaishengit.pojo.Worker;
import com.kaishengit.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Created by sunny on 2017/1/19.
 */
@Controller
@RequestMapping("/business/worker")
public class WorkerController {

    @Autowired
    private WorkerService workerService;

    @RequestMapping(method = RequestMethod.GET)
    public String workerList(Model model) {
        List<Worker> workerList = workerService.findAllWorker();
        model.addAttribute("workerList", workerList);
        return "/business/worker/list";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String workerNew() {
        return "/business/worker/new";
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String workerNew(Worker worker, RedirectAttributes redirectAttributes) {
        workerService.saveNewWork(worker);
        redirectAttributes.addFlashAttribute("message", "新工种添加成功！新工种名称：" + worker.getWorkerViewName());
        return "redirect:/business/worker";
    }

    @RequestMapping(value = "/{workerId:\\d+}/edit", method = RequestMethod.GET)
    public String workerEdit(@PathVariable Integer workerId, Model model) {
        Worker worker = workerService.findWorkerById(workerId);
        model.addAttribute("worker", worker);
        return "/business/worker/edit";
    }

    @RequestMapping(value = "/{workerId:\\d+}/edit", method = RequestMethod.POST)
    public String workerEdit(Worker worker, RedirectAttributes redirectAttributes) {
        workerService.updateWorker(worker);
        redirectAttributes.addFlashAttribute("message", "工种修改成功！修改工种名称：" + worker.getWorkerViewName());
        return "redirect:/business/worker";
    }

    @RequestMapping(value = "/{workerId:\\d+}/del", method = RequestMethod.GET)
    public String workerDel(@PathVariable Integer workerId, RedirectAttributes redirectAttributes) {
        workerService.delWorkerById(workerId);
        redirectAttributes.addFlashAttribute("message", "工种删除成功");
        return "redirect:/business/worker";
    }
}
