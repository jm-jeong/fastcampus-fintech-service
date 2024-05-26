package com.fastcampus.fintechservice.dto.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "oauth2.kakao")
public class KakaoOAuthProperties {
    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String grantType;
    private String scope;
    private String tokenUrl;
    private String userInfoUrl;
    private String tokenUrlType;
    private String userInfoUrlType;
}