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

/**
 * 지도관련 컨트롤러
 */
@Controller
public class MapServiceController {

    private MyWorkService service;

    @Autowired
    public void setMyWorkService(MyWorkService service) {
        this.service = service;
    }

    /**
     * 지도 화면 출력
     * @param request
     * @return
     */
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


    /**
     * 지도에 뿌릴 데이터
     * @param request
     * @param toDate
     * @return
     */
    @RequestMapping("/map/getData")
    @ResponseBody
    public Map<String, Object> getData(HttpServletRequest request,
                                       @RequestParam( value = "toDate") String toDate)  {

        Map<String, Object> resultMap = new HashMap<>();

        try {

            HttpSession session = request.getSession();

            Map<String, Object> param = new HashMap<>();
            //사용자 별 데이터를 가져오기 위해 사용자 정보는 세션에서 꺼내온다.
            MemberVO vo = (MemberVO)session.getAttribute("userInfo");
            param.put("empId", vo.getEmpId());
            param.put("toDate", toDate);
            // 사용자 , 일자별 데이터 가져오기
            List<PlaceVO> workList = service.selectTodoList(param);
            //리턴 객체에 담기
            resultMap.put("workList", workList);
        }catch (Exception e) {
            e.printStackTrace();
        }
        //ajax 데이터 리턴
        return resultMap;
    }



    @RequestMapping("/map/addplace")
    @ResponseBody
    public Map<String, Object> addPlace(HttpServletRequest request,
                                        PlaceVO svo)  {

        Map<String, Object> resultMap = new HashMap<>();

        try {

            HttpSession session = request.getSession();
            Map<String, Object> param = new HashMap<>();
            //사용자 별 데이터를 가져오기 위해 사용자 정보는 세션에서 꺼내온다.
            MemberVO vo = (MemberVO)session.getAttribute("userInfo");

            svo.setEmpId(vo.getEmpId());
            int result = service.insertTodoList(svo);

            if(result > 0) {
                resultMap.put("resultCode", 200);
            }else {
                resultMap.put("resultCode", 500);
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
        //ajax 데이터 리턴
        return resultMap;
    }

}
