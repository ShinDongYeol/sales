package com.sales.main.controller;

import com.sales.main.service.mywork.MyWorkService;
import com.sales.main.utils.DateUtils;
import com.sales.main.vo.StatusCodeVO;
import com.sales.main.vo.member.MemberVO;
import com.sales.main.vo.place.PlaceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
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

public class MyWorkController {

    private MyWorkService service;

    @Autowired
    public void setMyWorkService(MyWorkService service) {
        this.service = service;
    }

    @RequestMapping("/view/mywork")
    public ModelAndView loginView() {
        ModelAndView view = new ModelAndView();
        try {

            String nowDate = DateUtils.getNowTimeToString("yyyy-MM-dd");
            view.addObject("nowDate", nowDate);
            view.setViewName("views/mywork");

        }catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }


    @RequestMapping("/mywork/getData")
    @ResponseBody
    public Map<String, Object> getData(HttpServletRequest request,
                                       @RequestParam( value = "toDate") String toDate)  {

        Map<String, Object> resultMap = new HashMap<>();

        try {

            HttpSession session = request.getSession();

            Map<String, Object> param = new HashMap<>();

            MemberVO vo = (MemberVO)session.getAttribute("userInfo");
            param.put("empId", vo.getEmpId());
            param.put("toDate", toDate);

            List<StatusCodeVO> codeList = service.getStatus();
            List<PlaceVO> workList = service.selectTodoList(param);
            int goal = 0, done = 0;

            if(workList != null) {
                goal = workList.size();
                for(PlaceVO placeVO: workList) {
                    if(placeVO.getStatusName().equals("소싱")) {
                        done++;
                    }
                }
            }

            resultMap.put("goal", goal);
            resultMap.put("done", done);
            resultMap.put("workList", workList);
            resultMap.put("codeList", codeList);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    @RequestMapping("/mywork/modify")
    @ResponseBody
    public Map<String, Object> modifyWork(HttpServletRequest request,
                                       @RequestParam( value = "storeNo") int storeNo,
                                       @RequestParam( value = "storeId") String storeId,
                                       @RequestParam( value = "statusNo") int status)  {

        Map<String, Object> resultMap = new HashMap<>();

        try {
            HttpSession session = request.getSession();
            Map<String, Object> param = new HashMap<>();

            MemberVO vo = (MemberVO)session.getAttribute("userInfo");
            param.put("empId", vo.getEmpId());
            param.put("storeNo", storeNo);
            param.put("storeId", storeId);
            param.put("status", status);

            int result = service.updateWork(param);

            if(result > 0) {
                resultMap.put("resultCode", 200);
            }else {
                resultMap.put("resultCode", 500);
            }


        }catch (Exception e) {
            resultMap.put("resultCode", 500);
            e.printStackTrace();
        }

        return resultMap;

    }



    @RequestMapping("/mywork/delWork")
    @ResponseBody
    public Map<String, Object> delWork(HttpServletRequest request,
                                          @RequestParam( value = "storeNo") String storeNo){

        Map<String, Object> resultMap = new HashMap<>();

        try {
            HttpSession session = request.getSession();
            Map<String, Object> param = new HashMap<>();

            param.put("empId", "aa");
            param.put("storeNo", storeNo);

            int result = service.delWork(param);
            if(result > 0) {
                resultMap.put("resultCode", 200);
            }else {
                resultMap.put("resultCode", 500);
            }


        }catch (Exception e) {
            resultMap.put("resultCode", 500);
            e.printStackTrace();
        }

        return resultMap;

    }

}
