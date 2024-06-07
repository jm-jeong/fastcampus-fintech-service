package com.fastcampus.fintechservice.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;


@ToString
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class KakaoAccountDto {

    private String id;
    private KakaoAccount kakaoAccount;

    @ToString
    @Getter
    @NoArgsConstructor
    public static class KakaoAccount {

        private Profile profile;
        private String email;

        @ToString
        @Getter
        @NoArgsConstructor
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        public static class Profile {

            private String nickname;
        }
    }
}