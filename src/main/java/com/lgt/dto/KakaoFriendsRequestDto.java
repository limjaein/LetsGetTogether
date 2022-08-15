package com.lgt.dto;

import org.springframework.lang.Nullable;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class KakaoFriendsRequestDto {
    @Nullable
    Integer offset;
    @Nullable
    Integer limit;
}
