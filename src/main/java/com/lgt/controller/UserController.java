package com.lgt.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lgt.dto.KakaoFriendsResponseDto;
import com.lgt.service.KakaoApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {
	// TODO: UserController kakaoApiController 와 중복되는데 어떤걸로 사용할지 고민중

	private final KakaoApiService kakaoApiService;

	@RequestMapping("/user/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Optional<String> accessToken = getAccessToken(session);

		if (accessToken.isPresent() && !"".equals(accessToken.get())) {
			kakaoApiService.logout(accessToken.get());
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
			List<KakaoFriendsResponseDto.Friend> friends = kakaoApiService.getFriends(accessToken.get(), Optional.empty());
			model.addAttribute("friends", friends);
		}

		return "friends-list";
	}

	private Optional<String> getAccessToken(HttpSession session) {
		return Optional.ofNullable((String)session.getAttribute("access_token"));
	}
}
