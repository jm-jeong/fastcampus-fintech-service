package com.fastcampus.fintechservice.dto;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class UserInfoRequestDto {
    private String phoneNumber;
    // 주소
    private String address;
    // 성별
    private String gender;
    // 나이
    private int age;
    // 소비 성향 1순위
    private String spendType1st;
    // 소비 성향 2순위
    private String spendType2nd;
    // 수입최소
    private int incomeMin;
    // 수입최대
    private int incomeMax;
    // 신용점수
    private int creditScore;
}
