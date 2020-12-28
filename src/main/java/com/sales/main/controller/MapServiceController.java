package com.sales.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MapServiceController {

    @RequestMapping("/view/map")
    public ModelAndView mapView() {
        ModelAndView view = new ModelAndView();
        view.setViewName("views/map");
        return view;
    }

}
