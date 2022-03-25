package com.lgt.controller;

import java.util.Optional;

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
		Optional<String> accessToken = getAccessToken(request.getSession());

		if (accessToken.isPresent() && !"".equals(accessToken.get())) {
			kakaoapi.logout(accessToken.get());
			session.removeAttribute("access_token");
			session.removeAttribute("user");
			log.info("logout success");
		}

		return "redirect:/";
	}

	private Optional<String> getAccessToken(HttpSession session) {
		return Optional.ofNullable((String)session.getAttribute("access_token"));
	}
}
