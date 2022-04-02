package com.lgt.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.lgt.domain.Friend;
import com.lgt.service.KakaoAPIService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class IndexController {

	private final KakaoAPIService kakaoAPIService;

	@GetMapping("/")
	public String index() {
		return "webapp/bootstrap-index";
	}

	@GetMapping("/home")
	public String home(HttpSession session, Model model) {
		Optional<String> accessToken = Optional.ofNullable((String)session.getAttribute("access_token"));

		if (accessToken.isPresent() && !"".equals(accessToken.get())) {
			Optional<List<Friend>> freinds = kakaoAPIService.getFreinds(accessToken.get());
			if (freinds.isPresent()) {
				model.addAttribute("friends", freinds.get());
			}
		}
		return "webapp/bootstrap-home";
	}

}
