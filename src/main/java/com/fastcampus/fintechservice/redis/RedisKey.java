package com.fastcampus.fintechservice.redis;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RedisKey {

    DEPOSIT_LIKED_KEY("deposit_count:"),
    SAVING_LIKED_KEY("saving_count:")


    ;


    private final String key;
}
