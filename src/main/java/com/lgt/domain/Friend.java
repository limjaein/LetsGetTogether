package com.lgt.domain;

import lombok.Data;

@Data
public class Friend {
    Long id;
    String uuid;
    Boolean favorite;
    String profile_nickname;
    String profile_thumbnail_image;
    Boolean allowed_msg;
}
