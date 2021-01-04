package com.sales.main.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sales.main.service.todolist.TodoListService;
import com.sales.main.utils.DateUtils;
import com.sales.main.vo.place.PlaceVO;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TodoListController {

    private Logger logger = LoggerFactory.getLogger(TodoListController.class);

    private TodoListService service;

    @Autowired
    public TodoListController(TodoListService service) {
        this.service = service;
    }

    @RequestMapping("/view/todolist")
    public ModelAndView todoView() {
        ModelAndView view = new ModelAndView();
        try {
            String nowDate = DateUtils.getNowTimeToString("yyyy-MM-dd");
            view.addObject("nowDate", nowDate);
        }catch (Exception e) {
            logger.error("error",e);
        }
        view.setViewName("views/todolist");
        return view;
    }

    @RequestMapping(value = "/save/todo")
    @ResponseBody
    public Map<String, Object> saveStore(@RequestParam(value="nowDate") String nowDate,
                                         @RequestParam(value="dataList") String dataList) {

        Map<String, Object> resultMap = new HashMap<>();

        try {

            int result = service.insertTodoList(nowDate, dataList);

            if(result > 0) {
                resultMap.put("resultCode", 200);
            }else {
                resultMap.put("resultCode", 500);
            }

        }catch (Exception e) {
            resultMap.put("resultCode", 500);
            resultMap.put("resultMsg", e.getMessage());
            logger.error("error", e);
        }

        return resultMap;


    }
}
