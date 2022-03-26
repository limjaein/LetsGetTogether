package com.lgt.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lgt.domain.Friend;
import com.lgt.service.KakaoAPIService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

	private final KakaoAPIService kakaoAPIService;

	@RequestMapping("/user/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Optional<String> accessToken = getAccessToken(session);

		if (accessToken.isPresent() && !"".equals(accessToken.get())) {
			kakaoAPIService.logout(accessToken.get());
			session.removeAttribute("access_token");
			session.removeAttribute("user");
			log.info("logout success");
		}

		return "redirect:/";
	}

	@GetMapping("/user/friends")
	public String friends(HttpServletRequest request, Model model) {
		Optional<String> accessToken = getAccessToken(request.getSession());

		if (accessToken.isPresent() && !"".equals(accessToken.get())) {
			Optional<List<Friend>> freinds = kakaoAPIService.getFreinds(accessToken.get());

			if (freinds.isPresent()) {
				model.addAttribute("friends", freinds.get());
			}
		}

		return "friends_list";
	}

	// TODO: util 공통 함수로 빼기
	private Optional<String> getAccessToken(HttpSession session) {
		return Optional.ofNullable((String)session.getAttribute("access_token"));
	}
}
