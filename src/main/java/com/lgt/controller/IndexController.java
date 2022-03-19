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
	public String index(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String accessToken = (String)session.getAttribute("access_token");
		
		String htmlName = "index";
		
		if (accessToken != null && !"".equals(accessToken)) {
			htmlName = "home"; 
		}
		
		return htmlName;
	}
	
	@GetMapping("/home")
	public String home() {
		return "home";
	}
	
}
