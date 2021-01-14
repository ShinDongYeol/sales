package com.sales.main.controller;

import com.sales.main.service.member.MemberService;
import com.sales.main.vo.member.MemberVO;
import com.sales.main.vo.member.TeamVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class MemberController {

    private MemberService service;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;


    private Logger logger = LoggerFactory.getLogger(MemberController.class);

    @Autowired
    public MemberController(MemberService service) {
        this.service  = service;
    }

    @RequestMapping("/main/join")
    public ModelAndView loginView() {
        ModelAndView view = new ModelAndView();

        try{
            List<TeamVO> teamList = service.teamInfo();
            view.addObject("teamList", teamList);
        }catch (Exception e) {
            logger.error("error", e);
        }

        view.setViewName("views/join");
        return view;
    }

    @RequestMapping("/main/search/empId")
    @ResponseBody
    public Map<String, Object> search (@RequestParam( value = "empId") String empId){

        Map<String, Object> resultMap = new HashMap<>();

        try{


            Map<String, Object> param = new HashMap<>();
            param.put("empId", empId);
            int cnt =  service.searchMember(param);

            if(cnt > 0) {
                resultMap.put("isExist", "OK");
            }else{
                resultMap.put("isExist", "NO");
            }

        }catch (Exception e) {
            logger.error("error", e);
        }
        return resultMap;
    }

    @RequestMapping("/main/register")
    public ModelAndView registerMembr(MemberVO svo)  {

        ModelAndView view = new ModelAndView();
        view.setViewName("views/join_done");
        try{

            svo.setEmpPw(passwordEncoder.encode(svo.getEmpPw()));
            int result = service.insertMember(svo);

            if(result > 0) {
                view.addObject("result", 200);
            }else {
                view.addObject("result", 500);
            }

        }catch (Exception e) {
            logger.error("error", e);
            view.addObject("result", 500);
        }

        return view;
    }

}
