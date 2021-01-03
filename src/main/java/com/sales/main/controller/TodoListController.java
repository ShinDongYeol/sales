package com.sales.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TodoListController {

    @RequestMapping("/view/todolist")
    public ModelAndView todoView() {
        ModelAndView view = new ModelAndView();
        view.setViewName("views/todolist");
        return view;
    }
}
