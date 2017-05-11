package com.performance.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ShowPageController {

    @RequestMapping(value = "/1")
    public String showIndexPage() {
        System.out.println("1233");
        return "1";
    }

    @RequestMapping("/adminLogin")
    public String adminLogin() {
        return "adminLogin";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/register")
    public String register() {
        return "register";
    }
}