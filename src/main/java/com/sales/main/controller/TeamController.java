package com.sales.main.controller;

import com.sales.main.service.team.TeamService;
import com.sales.main.utils.DateUtils;
import com.sales.main.vo.myinfo.MyWorkInfoVO;
import com.sales.main.vo.team.TeamInfoVO;
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
public class TeamController {

    @Autowired
    TeamService service;

    @RequestMapping("/view/team")
    public ModelAndView loginView() {

        ModelAndView view = new ModelAndView();

        try {

            String nowDate = DateUtils.getNowTimeToString("yyyy-MM-dd");
            view.addObject("nowDate", nowDate);
            view.setViewName("views/team");

        }catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }


    @RequestMapping("/team/getData")
    @ResponseBody
    public Map<String, Object> getData(HttpServletRequest request,
                                       @RequestParam( value = "toDate") String toDate)  {

        Map<String, Object> resultMap = new HashMap<>();

        try {

            HttpSession session = request.getSession();
            Map<String, Object> param = new HashMap<>();
            param.put("toDate", toDate);

            List<TeamInfoVO> teamList = service.getTeamInfo(param);
            resultMap.put("teamList", teamList);

        }catch (Exception e) {
            e.printStackTrace();
        }
        return resultMap;
    }

}
