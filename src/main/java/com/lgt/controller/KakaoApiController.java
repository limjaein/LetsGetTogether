package com.lgt.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lgt.dto.KakaoFriendsResponseDto;
import com.lgt.service.KakaoApiService;
import com.lgt.support.Paging;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/kakao")
public class KakaoApiController {

    private final KakaoApiService kakaoApiService;

    @ApiResponse(description = "kakao-logout")
    @PostMapping("/logout")
    public void logout(HttpServletRequest request) {
        kakaoApiService.logout(getAccessToken(request));
    }

    @ApiResponse(description = "kakao-getfriends")
    @GetMapping("/friends")
    public List<KakaoFriendsResponseDto.Friend> getFriends(
        HttpServletRequest request,
        @RequestParam(required = false) Integer offset,
        @RequestParam(required = false) Integer limit) {
        if (offset != null && limit != null) {
            return kakaoApiService.getFriends(getAccessToken(request), Optional.of(new Paging(offset, limit)));
        } else {
            return kakaoApiService.getFriends(getAccessToken(request), Optional.empty());
        }
    }

    private String getAccessToken(HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            return (String)session.getAttribute("access_token");
        } catch (IllegalStateException ex) {
            log.error("KakaoApiController logout Error(access_token)!", ex);
            throw ex;
        }
    }
}
