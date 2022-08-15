package com.lgt.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lgt.client.KakaoApiClient;
import com.lgt.dto.KakaoFriendsResponseDto;
import com.lgt.support.Paging;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoApiService {

    private final KakaoApiClient kakaoApiClient;

    public void logout(String accessToken) {
        kakaoApiClient.logout(accessToken);
    }

    public List<KakaoFriendsResponseDto.Friend> getFriends(String accessToken, Optional<Paging> paging) {
        return kakaoApiClient.getFriends(accessToken, paging).getElements();
    }
}
