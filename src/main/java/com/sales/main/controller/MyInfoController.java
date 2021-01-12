package com.sales.main.controller;

import com.sales.main.service.myinfo.MyInfoService;
import com.sales.main.utils.DateUtils;
import com.sales.main.vo.StatusCodeVO;
import com.sales.main.vo.myinfo.MyWorkInfoVO;
import com.sales.main.vo.place.PlaceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller

public class MyInfoController {

    @Autowired
    private MyInfoService service;


    @RequestMapping("/view/myinfo")
    public ModelAndView loginView() {
        ModelAndView view = new ModelAndView();

        try {

            String nowDate = DateUtils.getNowTimeToString("yyyy-MM-dd");
            view.addObject("nowDate", nowDate);
            view.setViewName("views/myinfo");

        }catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }


    @RequestMapping("/myinfo/getData")
    @ResponseBody
    public Map<String, Object> getData(HttpServletRequest request,
                                       @RequestParam( value = "startDate") String startDate,
                                       @RequestParam( value = "endDate") String endDate)  {

        Map<String, Object> resultMap = new HashMap<>();

        try {

            HttpSession session = request.getSession();
            Map<String, Object> param = new HashMap<>();

            param.put("empId", "aa");
            param.put("startDate", startDate);
            param.put("endDate", endDate);

            List<MyWorkInfoVO> myworkList = service.getMyWorkInfo(param);
            resultMap.put("workList", myworkList);

        }catch (Exception e) {
            e.printStackTrace();
        }
        return resultMap;
    }

}
