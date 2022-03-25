package com.lgt.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class IndexController {

	@GetMapping("/")
	public String index(HttpServletRequest request) {
		Optional<String> accessToken = getAccessToken(request.getSession());
		if (accessToken.isPresent() && !"".equals(accessToken.get())) {
			return "home";
		} else {
			return "index";
		}
	}

	private Optional<String> getAccessToken(HttpSession session) {
		return Optional.ofNullable((String)session.getAttribute("access_token"));
	}
}
