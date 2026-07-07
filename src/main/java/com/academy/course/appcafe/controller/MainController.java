package com.academy.course.appcafe.controller;


import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping(value = "/main")
    public String showHomePage(){
        return "main-menu";
    }
    @GetMapping(value = "/manager")
    public String showManagerPage(){
        return "manager-page";
    }
    @GetMapping(value = "/admin")
    public String showAdminPage(){
        return "admin-page";
    }
    @GetMapping(value = "/cashier")
    public String showCashierPage(){
        return "cashier-page";
    }

}
