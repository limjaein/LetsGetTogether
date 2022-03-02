package com.lgt.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lgt.kakao.KakaoAPI;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class UserController {
	
	private KakaoAPI kakaoapi;
	
	public UserController() {
		kakaoapi = new KakaoAPI();
	}
	
	@RequestMapping("/user/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String accessToken = (String)session.getAttribute("access_token");
		
		if (accessToken != null && !"".equals(accessToken)) {
			kakaoapi.logout(accessToken);
			session.removeAttribute("access_token");
			session.removeAttribute("user");
			
			log.info("logout success");
		}
		
		return "redirect:/";
	}
}
