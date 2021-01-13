package com.sales.main.controller;

import com.sales.main.service.mywork.MyWorkService;
import com.sales.main.utils.DateUtils;
import com.sales.main.vo.StatusCodeVO;
import com.sales.main.vo.member.MemberVO;
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
public class MapServiceController {

    private MyWorkService service;

    @Autowired
    public void setMyWorkService(MyWorkService service) {
        this.service = service;
    }

    @RequestMapping("/view/map")
    public ModelAndView mapView(HttpServletRequest request) {
        ModelAndView view = new ModelAndView();

        try {

            String nowDate = DateUtils.getNowTimeToString("yyyy-MM-dd");
            view.addObject("nowDate", nowDate);

        }catch (Exception e) {
            e.printStackTrace();
        }

        view.setViewName("views/map");
        return view;
    }


    @RequestMapping("/map/getData")
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

            List<PlaceVO> workList = service.selectTodoList(param);


            resultMap.put("workList", workList);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return resultMap;
    }

}
