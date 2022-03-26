package com.lgt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class IndexController {

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/home")
	public String home() {
		return "home";
	}

}
