package com.lgt.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.lgt.dto.KakaoFriendsResponseDto;
import com.lgt.service.KakaoApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class IndexController {

	private final KakaoApiService kakaoApiService;

	@GetMapping("/")
	public String index() {
		return "webapp/bootstrap-index";
	}

	@GetMapping("/home")
	public String home(HttpSession session, Model model) {
		Optional<String> accessToken = Optional.ofNullable((String)session.getAttribute("access_token"));

		if (accessToken.isPresent() && !"".equals(accessToken.get())) {
			List<KakaoFriendsResponseDto.Friend> friends = kakaoApiService.getFriends(accessToken.get(), Optional.empty());
			model.addAttribute("friends", friends);
		}
		return "webapp/bootstrap-home";
	}

}
