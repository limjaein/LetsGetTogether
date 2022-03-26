package com.lgt.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class IndexController {

	@GetMapping("/")
	public String main(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String accessToken = (String)session.getAttribute("access_token");
		
		if (accessToken != null && !"".equals(accessToken)) {
			return "redirect:/home";
		}
		
		return "redirect:/index";
	}

	@GetMapping("/home")
	public String home() {
		return "home";
	}
	
	@GetMapping("/index")
	public String index() {
		return "index";
	}
	
}
