package com.fastcampus.fintechservice.service;

import com.fastcampus.fintechservice.config.client.KakaoWebClient;
import com.fastcampus.fintechservice.config.utils.JwtTokenUtils;
import com.fastcampus.fintechservice.db.user.UserAccount;
import com.fastcampus.fintechservice.db.user.UserRepository;
import com.fastcampus.fintechservice.dto.KakaoAccountDto;
import com.fastcampus.fintechservice.dto.response.KakaoTokenResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class KakaoAuthService {
    private final UserRepository userRepository;
    private final KakaoWebClient kakaoWebClient;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token.expired-time-ms}")
    private Long expiredTimeMs;



    @Transactional
    public String loginKakao(String code) {
        String accessToken = getKakaoToken(code).getAccessToken();
        log.info("카카오 토큰 발급 완료 {}", accessToken);
        KakaoAccountDto kakaoAccountDto = getKakaoInfo(accessToken);
        log.info("카카오 정보 조회 완료 {}", kakaoAccountDto.getKakaoAccount().getEmail());
        return userRepository.findByEmail(kakaoAccountDto.getKakaoAccount().getEmail())
                .map(user -> {
                    log.info("카카오 로그인 성공 {}", user.getEmail());
                    return JwtTokenUtils.generateAccessToken(user.getEmail(), secretKey, expiredTimeMs);
                })
                .orElseGet(() -> signUpKakao(kakaoAccountDto));
    }



    // 4.가입정보가 없다면 회원가입 처리
    @Transactional
    public String signUpKakao(KakaoAccountDto accountDto) {
        UserAccount user = UserAccount.builder()
                .kakaoId(accountDto.getId())
                .email(accountDto.getKakaoAccount().getEmail())
                .name(accountDto.getKakaoAccount().getProfile().getNickname())
                .build();
        userRepository.save(user);
        // 5. 가입과 동시에 로그인 : 토큰발급

        return JwtTokenUtils.generateAccessToken(user.getEmail(), secretKey, expiredTimeMs);
    }

    private KakaoTokenResponseDto getKakaoToken(String code) {

        return kakaoWebClient.getKakaoToken(code);
    }

    private KakaoAccountDto getKakaoInfo(String accessToken) {

        return kakaoWebClient.getKakaoInfo(accessToken);
    }

}
