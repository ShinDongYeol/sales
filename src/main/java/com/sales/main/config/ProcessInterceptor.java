package com.sales.main.config;


import com.sales.main.vo.member.MemberVO;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ProcessInterceptor implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        boolean result = false;

        try {
            MemberVO adminUser = (MemberVO) request.getSession().getAttribute("userInfo");
            if(adminUser == null ){
                if(isAjaxRequest(request)) {
                    response.sendError(405);
                    return false;
                }else{
                    response.sendRedirect("/login/login");
                    result =  false;
                }
            }else{
                result =  true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return false;
        }
        return result;
    }

    private boolean isAjaxRequest(HttpServletRequest req) {
        String header = req.getHeader("AJAX");
        if ("true".equals(header)){
            return true;
        }else{
            return false;
        }
    }
}
