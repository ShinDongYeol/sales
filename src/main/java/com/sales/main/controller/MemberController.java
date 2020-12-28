package com.sales.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class MemberController {

    @RequestMapping("/view/join")
    public ModelAndView loginView() {
        ModelAndView view = new ModelAndView();
        view.setViewName("views/join");
        return view;
    }

}
