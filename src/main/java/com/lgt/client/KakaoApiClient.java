package com.lgt.client;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.lgt.dto.KakaoFriendsRequestDto;
import com.lgt.dto.KakaoFriendsResponseDto;
import com.lgt.support.Paging;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoApiClient {

    private final String AUTHORIZATION = "Authorization";
    private final String BEARER_PREFIX = "Bearer ";

    private final String logoutURL = "/user/logout";
    private final String friendsURL = "/api/talk/friends";

    @Value("${api.kakao}")
    private String KAKAO_API_URL;

    private final RestTemplate restTemplate;

    public void logout(String accessToken) {
        try {
            URL url = new URL(KAKAO_API_URL + logoutURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty(AUTHORIZATION, BEARER_PREFIX + accessToken);

            conn.disconnect();
        } catch (Exception ex) {
            // TODO: Exception 세분화
            log.error("KakaoAPI logout Error!", ex);
        }
    }

    public KakaoFriendsResponseDto getFriends(String accessToken, Optional<Paging> paging) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(AUTHORIZATION, BEARER_PREFIX + accessToken);

            KakaoFriendsRequestDto kakaoFriendsRequestDto = null;
            if (paging.isPresent()) {
                kakaoFriendsRequestDto = KakaoFriendsRequestDto.builder()
                    .offset(paging.get().getOffset())
                    .limit(paging.get().getLimit())
                    .build();
            }
            if (kakaoFriendsRequestDto != null) log.info("request: {}", kakaoFriendsRequestDto);
            HttpEntity<KakaoFriendsRequestDto> entity = new HttpEntity<>(kakaoFriendsRequestDto, headers);
            ResponseEntity<KakaoFriendsResponseDto> response =
                restTemplate.exchange(KAKAO_API_URL + friendsURL, HttpMethod.GET, entity, KakaoFriendsResponseDto.class);
            if (response.hasBody()) {
                log.info(response.getBody().toString());
                return response.getBody();
            } else {
                throw new RuntimeException("KakaoAPI getFriends request error! No response body!");
            }
        } catch (Exception ex) {
            // TODO: Exception 세분화
            log.error("KakaoAPI getFriends request error!", ex);
            throw ex;
        }
    }
}
