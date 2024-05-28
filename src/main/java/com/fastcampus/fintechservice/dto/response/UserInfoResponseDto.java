package com.fastcampus.fintechservice.dto.response;

import com.fastcampus.fintechservice.db.user.UserAccount;
import lombok.Builder;
import lombok.Getter;


@Builder
@Getter
public class UserInfoResponseDto {
    private String name;
    private String email;
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

    public static UserInfoResponseDto from(UserAccount userAccount) {
        return UserInfoResponseDto.builder()
                .name(userAccount.getName())
                .email(userAccount.getEmail())
                .address(userAccount.getUserInfo().getAddress())
                .phoneNumber(userAccount.getUserInfo().getPhoneNumber())
                .gender(userAccount.getUserInfo().getGender())
                .age(userAccount.getUserInfo().getAge())
                .spendType1st(userAccount.getUserInfo().getSpendType1st())
                .spendType2nd(userAccount.getUserInfo().getSpendType2nd())
                .incomeMin(userAccount.getUserInfo().getIncomeMin())
                .incomeMax(userAccount.getUserInfo().getIncomeMax())
                .creditScore(userAccount.getUserInfo().getCreditScore())
                .build();
    }
}
