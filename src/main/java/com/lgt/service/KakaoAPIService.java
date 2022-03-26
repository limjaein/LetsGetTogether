package com.lgt.service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lgt.domain.Friend;
import com.lgt.dto.KaKaoFriendResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoAPIService {

	private final String AUTHORIZATION = "Authorization";
	private final String BEARER_PREFIX = "Bearer ";

	private final String logoutURL = "/user/logout";
	private final String friendsURL = "/api/talk/friends";

	@Value("${api.kakao}")
	private String KAKAO_API_URL;

	private final RestTemplate restTemplate;
	private final ObjectMapper objectMapper;

	public void logout(String accessToken) {
		try {
			URL url = new URL(KAKAO_API_URL + logoutURL);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty(AUTHORIZATION, BEARER_PREFIX + accessToken);

			conn.disconnect();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Optional<List<Friend>> getFreinds(String accessToken) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(AUTHORIZATION, BEARER_PREFIX + accessToken);

			HttpEntity<String> entity = new HttpEntity<String>("", headers);
			ResponseEntity<KaKaoFriendResponseDto> response = restTemplate.exchange(KAKAO_API_URL + friendsURL, HttpMethod.GET, entity, KaKaoFriendResponseDto.class);
			if (response.hasBody()) {
				return Optional.ofNullable(response.getBody().getElements());
			}
			log.info("KakaoAPI getFriends request error! No response body!");
		} catch (HttpStatusCodeException ex) {
			log.error("KakaoAPI getFriends request error! statusCode: {}, body: {}", ex.getRawStatusCode(), ex.getResponseBodyAsString());
		} catch (ResourceAccessException ex) {
			log.error("KakaoAPI getFriends request error! message: {}", ex.getMessage());
		}

		return Optional.empty();
	}
}
