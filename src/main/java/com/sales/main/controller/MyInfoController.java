package com.sales.main.controller;

import com.sales.main.service.myinfo.MyInfoService;
import com.sales.main.utils.DateUtils;
import com.sales.main.vo.StatusCodeVO;
import com.sales.main.vo.member.MemberVO;
import com.sales.main.vo.member.TeamVO;
import com.sales.main.vo.myinfo.MyWorkInfoVO;
import com.sales.main.vo.place.PlaceVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    private Logger logger = LoggerFactory.getLogger(MyInfoController.class);

    @RequestMapping("/view/myinfo")
    public ModelAndView myinfoView(HttpServletRequest request) {
        ModelAndView view = new ModelAndView();

        try {

            HttpSession session = request.getSession();
            MemberVO vo = (MemberVO)session.getAttribute("userInfo");
            String nowDate = DateUtils.getNowTimeToString("yyyy-MM-dd");
            view.addObject("nowDate", nowDate);
            view.addObject("mem", vo);
            view.setViewName("views/myinfo");

        }catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }



    @RequestMapping("/view/modifyInfo")
    public ModelAndView modifyInfo(HttpServletRequest request) {
        ModelAndView view = new ModelAndView();

        try {
            String nowDate = DateUtils.getNowTimeToString("yyyy-MM-dd");
            HttpSession session = request.getSession();

            MemberVO vo = (MemberVO)session.getAttribute("userInfo");
            List<TeamVO> teamList = service.teamInfo();

            view.addObject("teamList", teamList);
            view.addObject("mem", vo);
            view.addObject("nowDate", nowDate);
            view.setViewName("views/modifyInfo");

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

            MemberVO vo = (MemberVO)session.getAttribute("userInfo");

            param.put("empId", vo.getEmpId());
            param.put("startDate", startDate);
            param.put("endDate", endDate);

            List<MyWorkInfoVO> myworkList = service.getMyWorkInfo(param);
            resultMap.put("workList", myworkList);

        }catch (Exception e) {
            e.printStackTrace();
        }
        return resultMap;
    }


    @RequestMapping("/myinfo/modify")
    @ResponseBody
    public Map<String, Object> modifyMyinfo(  HttpServletRequest request,
                                                @RequestParam(value = "empId") String empId,
                                                 @RequestParam(value = "empPw") String empPw,
                                                @RequestParam(value = "teamCode") String teamCode){


        Map<String, Object> resultMap = new HashMap<>();

        try{

            Map<String, Object> param = new HashMap<>();
            param.put("empId", empId);
            param.put("teamCode", teamCode);
            if(empPw !=null && empPw.length() > 0) {
                param.put("empPw", passwordEncoder.encode(empPw));
            }
            int result = service.updateMember(param);

            if(result > 0) {
                resultMap.put("resultCode", 200);

                HttpSession session = request.getSession();
                MemberVO vo = (MemberVO)session.getAttribute("userInfo");


                //팀정보 갱신
                MemberVO newVO = service.getMemberInfo(param);
                vo.setTeamCode(teamCode);
                vo.setTeam(newVO.getTeam());

                session.removeAttribute("userInfo");
                session.setAttribute("userInfo", vo);

            }else {
                resultMap.put("resultCode", 500);
            }

        }catch (Exception e) {
            logger.error("error", e);
            resultMap.put("resultCode", 500);
        }

        return resultMap;

    }

}
