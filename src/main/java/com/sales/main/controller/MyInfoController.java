package com.sales.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller

public class MyInfoController {


    @RequestMapping("/view/myinfo")
    public ModelAndView loginView() {
        ModelAndView view = new ModelAndView();
        view.setViewName("views/myinfo");
        return view;
    }

}
