package com.fastcampus.fintechservice.db.user;


import com.fastcampus.fintechservice.dto.UserInfoRequestDto;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor
public class UserInfo {

    // 핸드폰 번호
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
    // 은행

    // 카드회사

    public void updateUserInfo(UserInfoRequestDto userInfoRequestDto) {
        this.phoneNumber = userInfoRequestDto.getPhoneNumber();
        this.address = userInfoRequestDto.getAddress();
        this.age = userInfoRequestDto.getAge();
        this.spendType1st = userInfoRequestDto.getSpendType1st();
        this.spendType2nd = userInfoRequestDto.getSpendType2nd();
        this.gender = userInfoRequestDto.getGender();
        this.incomeMax = userInfoRequestDto.getIncomeMax();
        this.incomeMin = userInfoRequestDto.getIncomeMin();
        this.creditScore = userInfoRequestDto.getCreditScore();
    }
}
