package com.kaishengit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by sunny on 2017/2/9.
 */
@Controller
@RequestMapping("/finance")
public class FinanceController {

    @RequestMapping(value = "/day", method = RequestMethod.GET)
    public String day() {
        return "/finance/day";
    }

    @RequestMapping(value = "/mounth", method = RequestMethod.GET)
    public String month() {
        return "/finance/month";
    }

    @RequestMapping(value = "year", method = RequestMethod.GET)
    public String year() {
        return "/finance/year";
    }
}
