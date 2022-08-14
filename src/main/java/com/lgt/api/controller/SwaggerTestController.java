package com.lgt.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api")
public class SwaggerTestController {

    @ApiResponse(description = "test")
    @GetMapping("/test")
    public String test(@RequestParam String text) {
        return text;
    }
}
