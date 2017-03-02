package com.kaishengit.controller;

import com.kaishengit.exception.NotFoundException;
import com.kaishengit.pojo.Role;
import com.kaishengit.pojo.User;
import com.kaishengit.service.UserService;
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
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) {
        List<User> userList = userService.findAllUser();
        model.addAttribute("userList", userList);
        return "user/list";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newUser(Model model) {
        List<Role> roleList = userService.findAllRole();
        model.addAttribute("roleList", roleList);
        return "user/new";
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String newUser(User user, Integer[] roleIds, RedirectAttributes redirectAttributes) {
        Integer newAccount = userService.saveNewUser(user, roleIds);
        redirectAttributes.addFlashAttribute("message", "新建账户成功！帐户名：" + newAccount);
        return "redirect:/user";
    }

    @RequestMapping(value = "/{userId:\\d+}/del", method = RequestMethod.GET)
    public String delUser(@PathVariable Integer userId, RedirectAttributes redirectAttributes) {
        userService.delUserById(userId);
        redirectAttributes.addFlashAttribute("message", "成功删除用户");
        return "redirect:/user";
    }

    @RequestMapping(value = "/{userId:\\d+}/edit", method = RequestMethod.GET)
    public String editUser(@PathVariable Integer userId, Model model) {
        User user = userService.findUserById(userId);
        if (user != null) {
            List<Role> roleList = userService.findAllRole();
            model.addAttribute("user", user);
            model.addAttribute("roleList", roleList);
            return "/user/edit";
        } else {
            throw new NotFoundException();
        }
    }

    @RequestMapping(value = "/{userId:\\d+}/edit", method = RequestMethod.POST)
    public String editUser(User user, Integer[] roleIds, RedirectAttributes redirectAttributes) {
        userService.editUser(user, roleIds);
        redirectAttributes.addFlashAttribute("message", "编辑成功");
        return "redirect:/user";
    }
}
