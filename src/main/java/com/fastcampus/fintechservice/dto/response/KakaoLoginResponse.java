package com.fastcampus.fintechservice.dto.response;



public record KakaoLoginResponse(String accessToken) {

    public static KakaoLoginResponse of(String accessToken) {
        return new KakaoLoginResponse(accessToken);
    }

}
