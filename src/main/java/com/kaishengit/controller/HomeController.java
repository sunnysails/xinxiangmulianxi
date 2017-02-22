package com.kaishengit.controller;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletRequest;


/**
 * Created by sunny on 2017/1/16.
 */
@Controller
public class HomeController {
    @Value("${password.salt}")
    private String salt;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String login(String userName, String password, RedirectAttributes redirectAttributes) {
        //Shiro方式登录
        Subject subject = SecurityUtils.getSubject();
        password = DigestUtils.md5Hex(password + salt);
        try {
            subject.login(new UsernamePasswordToken(userName, password));
            return "redirect:/home";
        } catch (AuthenticationException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "账号或密码错误");
            return "redirect:/";
        }
    }

    //    @GetMapping("/home")
//    public SavedRequest getSavedRequest() {
//        SavedRequest savedRequest = null;
//        Subject subject = SecurityUtils.getSubject();
//        Session session = subject.getSession(true);
//        if (session != null) {
//            savedRequest = (SavedRequest) session.getAttribute(WebUtils.SAVED_REQUEST_KEY);
//        }
//        return savedRequest;
//    }
    @GetMapping("/logout")
    public String logout(RedirectAttributes redirectAttributes) {
        //安全退出
        SecurityUtils.getSubject().logout();
        redirectAttributes.addFlashAttribute("message", "您已安全退出！");
        return "redirect:/";
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String go(ServletRequest request){
        //获取登录前的Url
        try {
            String url = WebUtils.getSavedRequest(request).getRequestUrl();
            if (!url.isEmpty() && !url.equals("/favicon.ico")) {
                return "redirect:" + url;
            } else {
                return "home";
            }
        }catch (NullPointerException e){
            return "home";
        }
    }

    @RequestMapping("/403")
    public String error403() {
        return "403";
    }
}