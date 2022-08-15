package com.lgt.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.lang.Nullable;

import lombok.Data;

@Data
public class KakaoFriendsResponseDto {
    @NotNull
    Integer total_count;
    @Nullable
    String before_url;
    @Nullable
    String after_url;
    @Nullable
    Integer favorite_count;
    @Nullable
    List<Friend> elements;

    @Data
    public static class Friend {
        @NotNull
        Long id;
        @NotNull
        String uuid;
        @Nullable
        Boolean favorite;
        @Nullable
        String profile_nickname;
        @Nullable
        String profile_thumbnail_image;
    }
}
