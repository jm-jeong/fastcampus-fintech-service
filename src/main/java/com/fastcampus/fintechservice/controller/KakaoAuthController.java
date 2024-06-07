package com.fastcampus.fintechservice.controller;

import com.fastcampus.fintechservice.common.api.Api;
import com.fastcampus.fintechservice.dto.response.KakaoLoginResponse;
import com.fastcampus.fintechservice.dto.response.UserResponse;
import com.fastcampus.fintechservice.service.KakaoAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class KakaoAuthController {

    private final KakaoAuthService kakaoAuthService;

    @GetMapping("/login/kakao")
    public Api<KakaoLoginResponse> loginKakao(@RequestParam("code") String code) {

        return Api.OK(KakaoLoginResponse.of(kakaoAuthService.loginKakao(code)));
    }
}
