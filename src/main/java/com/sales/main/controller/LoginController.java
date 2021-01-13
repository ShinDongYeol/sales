package com.sales.main.controller;

import com.sales.main.security.ZRsaSecurity;
import com.sales.main.service.member.MemberService;
import com.sales.main.vo.member.MemberVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private MemberService service;

	Logger logger = LoggerFactory.getLogger(LoginController.class);


	@RequestMapping("/view/login")
	public ModelAndView loginView() {
		ModelAndView view = new ModelAndView();
		view.setViewName("login/home");
		return view;
	}


	@RequestMapping("/login/getRSAKeyValue")
	@ResponseBody
	public Map<String, Object> getRSAKeyValue(HttpServletRequest req, HttpServletResponse res) throws Exception {

		Map<String,Object> resultMap = new HashMap<>();

		try {

			ZRsaSecurity zSecurity = new ZRsaSecurity();
			PrivateKey privateKey = zSecurity.getPrivateKey();

			HttpSession session = req.getSession();

			if(session.getAttribute("_rsaPrivateKey_") !=null) {
				session.removeAttribute("_rsaPrivateKey_");
			}

			session.setAttribute("_rsaPrivateKey_", privateKey);

			String publicKeyModulus = zSecurity.getRsaPublicKeyModulus();
			String publicKeyExponent = zSecurity.getRsaPublicKeyExponent();

			resultMap.put("publicKeyModulus", publicKeyModulus);
			resultMap.put("publicKeyExponent", publicKeyExponent);
		}catch (Exception e){

		}

		return resultMap;
	}


	@RequestMapping(value = "/login/loginajax", method = RequestMethod.POST)
	@ResponseBody
	public ModelMap login(HttpServletRequest request, HttpServletResponse response,
						  @RequestParam(value = "userId") String username,
						  @RequestParam(value = "userKey") String password) {

		ModelMap map = new ModelMap();

		try {

			ZRsaSecurity rsa = new ZRsaSecurity();

			HttpSession session = request.getSession();
			PrivateKey privateKey = (PrivateKey) session.getAttribute("_rsaPrivateKey_");

			String userId = rsa.decryptRSA(privateKey, username);
			String userKey = rsa.decryptRSA(privateKey, password);


			Map<String, Object> param = new HashMap<>();
			param.put("empId", userId);

			MemberVO userInfo = service.getMemberInfo(param);

			if (userInfo != null) {  // 사용자가 존재한다면

				boolean math = passwordEncoder.matches(userKey, userInfo.getEmpPw());

				if (math) {
					userInfo.setEmpPw(""); // 비밀번호는 그냥 안보이도롯 초기화

					session.removeAttribute("_rsaPrivateKey_"); // 키의 재사용을 막는다. 다만 로그인되기전까지만 유지...
					session.setAttribute("userInfo", userInfo ); // 세션에다가 사용자 정보 유지
					session.setMaxInactiveInterval(60 * 30); // 30분 타임제한...

					map.put("resultCode", 200);
					map.put("resultMsg", "OK");
					map.put("userVO", userInfo);
				} else {
					map.put("resultCode", 400);
					map.put("resultMsg", "비밀번호를 확인해주십시오.");
				}

			} else {
				map.put("resultCode", 400);
				map.put("resultMsg", "아이디가 존재하지 않습니다.");
			}


		} catch (Exception e) {
			logger.error("loginError ", e);
			map.put("resultCode", 500);
			map.put("resultMsg", "Error");
		}
		return map;
	}


	@RequestMapping("/logout")
	public ModelAndView loginOutUser(HttpServletRequest req, HttpServletResponse res){
		ModelAndView view = new ModelAndView();
		try{
			HttpSession session = req.getSession();
			if(session.getAttribute("userInfo") !=null){
				session.removeAttribute("userInfo");
				view.addObject("resultCode", 200);
				view.addObject("resultMsg", "OK");
			}

		}catch(Exception e){
			view.addObject("resultCode", 400);
			view.addObject("resultMsg", "fail");
			e.printStackTrace();
		}
		view.setViewName("login/home");

		return view;
	}
	
}
