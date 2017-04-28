package com.performance.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ShowPageController {

    @RequestMapping(value = "/12")
    public String showIndexPage() {
        System.out.println("1233");
        return "1";
    }

}