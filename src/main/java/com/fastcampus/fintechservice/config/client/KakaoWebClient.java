package com.fastcampus.fintechservice.config.client;



import com.fastcampus.fintechservice.dto.KakaoAccountDto;
import com.fastcampus.fintechservice.dto.properties.KakaoOAuthProperties;
import com.fastcampus.fintechservice.dto.response.KakaoTokenResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@Slf4j
@RequiredArgsConstructor
public class KakaoWebClient {

    private final KakaoOAuthProperties properties;

    public KakaoTokenResponseDto getKakaoToken(String code) {
        String url = UriComponentsBuilder.fromHttpUrl(properties.getTokenUrl()).toUriString();
        WebClient webClient = WebClient.builder()
                .baseUrl(url)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .build();
        String uri = UriComponentsBuilder.newInstance()
                .path(properties.getTokenUrlType())
                .queryParam("grant_type", properties.getGrantType())
                .queryParam("client_id", properties.getClientId())
                .queryParam("redirect_uri", properties.getRedirectUri())
                .queryParam("code", code)
                .queryParam("client_secret", properties.getClientSecret())
                .build(true)
                .toString();
        return webClient.post()
                .uri(uri)
                .retrieve()
                .bodyToMono(KakaoTokenResponseDto.class)
                .block();

    }
    public KakaoAccountDto getKakaoInfo(String accessToken) {
        String url = UriComponentsBuilder.fromHttpUrl(properties.getUserInfoUrl()).toUriString();
        WebClient webClient = WebClient.builder()
                .baseUrl(url)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .build();
        String uri = UriComponentsBuilder.newInstance()
                .path(properties.getUserInfoUrlType())
                .build(true)
                .toString();
        return webClient.post()
                .uri(uri)
                .retrieve()
                .bodyToMono(KakaoAccountDto.class)
                .block();
    }
}
