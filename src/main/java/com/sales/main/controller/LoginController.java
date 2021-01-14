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

/**
 * 로그인 / 회원가입 관련 컨트롤러
 */
@Controller
public class LoginController {

	// 비밀번호 암호화를 위한 패스워드 인코더
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	// 사용자 서비스
	@Autowired
	private MemberService service;
	
	//로그를 남기기 위한 객체 선언
	Logger logger = LoggerFactory.getLogger(LoginController.class);


	/**
	 * 로그인화면 
	 * @return
	 */
	@RequestMapping("/login/login")
	public ModelAndView loginView() {
		ModelAndView view = new ModelAndView();
		view.setViewName("login/home");
		return view;
	}


	/**
	 * RSA 암호화를 위한
	 * 공개키와 비밀 키생성 로직
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/login/getRSAKeyValue")
	@ResponseBody
	public Map<String, Object> getRSAKeyValue(HttpServletRequest req, HttpServletResponse res) throws Exception {

		Map<String,Object> resultMap = new HashMap<>();

		try {

			//RSA 객체 선언
			ZRsaSecurity zSecurity = new ZRsaSecurity();
			//비공개 키 가져오기
			PrivateKey privateKey = zSecurity.getPrivateKey();

			//세션 객체 가져오기
			HttpSession session = req.getSession();

			// 이미 존재하는게 있다면 우선 지운다.
			if(session.getAttribute("_rsaPrivateKey_") !=null) {
				session.removeAttribute("_rsaPrivateKey_");
			}

			//비공개키는 보이면 안되니싸 세션에서 제어
			session.setAttribute("_rsaPrivateKey_", privateKey);

			// 암호화를 위한 공개키 생성
			String publicKeyModulus = zSecurity.getRsaPublicKeyModulus();
			String publicKeyExponent = zSecurity.getRsaPublicKeyExponent();

			//공개키는 화면으로 넘긴다.
			resultMap.put("publicKeyModulus", publicKeyModulus);
			resultMap.put("publicKeyExponent", publicKeyExponent);
		}catch (Exception e){
			logger.error("error", e);
		}

		//ajax 리턴
		return resultMap;
	}


	/**
	 * 로그인 처리
	 * @param request
	 * @param response
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "/login/loginajax", method = RequestMethod.POST)
	@ResponseBody
	public ModelMap login(HttpServletRequest request, HttpServletResponse response,
						  @RequestParam(value = "userId") String username,
						  @RequestParam(value = "userKey") String password) {

		//모델 객체 선언
		ModelMap map = new ModelMap();

		try {

			// RSA 객체 가져오기
			ZRsaSecurity rsa = new ZRsaSecurity();

			//세션에서 비공개 키 를 꺼낸다.
			HttpSession session = request.getSession();
			PrivateKey privateKey = (PrivateKey) session.getAttribute("_rsaPrivateKey_");

			//비공캐키를 이용하여 복호화
			String userId = rsa.decryptRSA(privateKey, username);
			String userKey = rsa.decryptRSA(privateKey, password);

			//사용자 정보확인하기 위해 파라미터 만들기
			Map<String, Object> param = new HashMap<>();
			param.put("empId", userId);

			// 넘어온 사용자 ID로 정보가져오기
			MemberVO userInfo = service.getMemberInfo(param);

			//사용자가 존재하면
			if (userInfo != null) {  // 사용자가 존재한다면
				//비밀번호가 일치하는지 비교
				boolean math = passwordEncoder.matches(userKey, userInfo.getEmpPw());

				//비번이 일치하면 로그인 진행
				if (math) {
					userInfo.setEmpPw(""); // 비밀번호는 그냥 안보이도롯 초기화

					session.removeAttribute("_rsaPrivateKey_"); // 키의 재사용을 막는다. 다만 로그인되기전까지만 유지...
					session.setAttribute("userInfo", userInfo ); // 세션에다가 사용자 정보 유지
					session.setMaxInactiveInterval(60 * 30); // 30분 타임제한...

					map.put("resultCode", 200);
					map.put("resultMsg", "OK");
					map.put("userVO", userInfo);
				} else {
					//비밀번호 불일치시 에러 처리
					map.put("resultCode", 400);
					map.put("resultMsg", "비밀번호를 확인해주십시오.");
				}

			} else {
				//아이디 없을 경우 에러처리
				map.put("resultCode", 400);
				map.put("resultMsg", "아이디가 존재하지 않습니다.");
			}


		} catch (Exception e) {
			// 예외처리 로그
			logger.error("loginError ", e);
			map.put("resultCode", 500);
			map.put("resultMsg", "Error");
		}
		return map;
	}


	/**
	 * 로그아웃 기능 처리
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("/logout")
	public ModelAndView loginOutUser(HttpServletRequest req, HttpServletResponse res){
		ModelAndView view = new ModelAndView();
		try{
			HttpSession session = req.getSession();
			//사용자 세션 삭제
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
		//메인화면으로 돌린다.
		view.setViewName("login/home");

		return view;
	}
	
}
