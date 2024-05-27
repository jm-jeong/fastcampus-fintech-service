package com.fastcampus.fintechservice.controller;

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
    public String loginKakao(@RequestParam("code") String code) {

        return kakaoAuthService.loginKakao(code);
    }
}
