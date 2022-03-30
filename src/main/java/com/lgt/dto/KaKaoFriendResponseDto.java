package com.lgt.dto;

import java.util.List;

import com.lgt.domain.Friend;
import lombok.Data;

@Data
public class KaKaoFriendResponseDto {
    Integer total_count;
    String before_url;
    String after_url;
    Integer favorite_count;
    List<Friend> elements;
}
