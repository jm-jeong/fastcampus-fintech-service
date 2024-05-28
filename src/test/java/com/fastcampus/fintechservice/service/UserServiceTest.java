package com.fastcampus.fintechservice.service;

import com.fastcampus.fintechservice.db.user.UserAccount;
import com.fastcampus.fintechservice.db.user.UserInfo;
import com.fastcampus.fintechservice.dto.UserInfoRequestDto;
import com.fastcampus.fintechservice.dto.response.UserInfoResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("회원 상세 정보 업데이트")
    void updateDetailInfoTest() {
        //given
        UserInfoRequestDto request = UserInfoRequestDto.builder()
                .phoneNumber("010-1780-5770")
                .address("서울특별시 구로구 개봉로15길 36-21(개봉동) 08346 한국")
                .age(23)
                .spendType1st("대중교통")
                .spendType2nd("외식")
                .gender("Men")
                .incomeMin(0)
                .incomeMax(500)
                .creditScore(820)
                .build();

// UserInfo 객체 생성
        UserInfo userInfo = UserInfo.builder()
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .age(request.getAge())
                .spendType1st(request.getSpendType1st())
                .spendType2nd(request.getSpendType2nd())
                .gender(request.getGender())
                .incomeMin(request.getIncomeMin())
                .incomeMax(request.getIncomeMax())
                .creditScore(request.getCreditScore())
                .build();


        UserAccount user = UserAccount.builder()
                .name("김도하")
                .email("kim.doha@example.com")
                .password("password")
                .userInfo(userInfo)
                .build();


        UserInfoResponseDto result = UserInfoResponseDto.from(user);

        //when
        assertEquals(request.getPhoneNumber(), result.getPhoneNumber());
        assertEquals(request.getAddress(), result.getAddress());
        assertEquals(request.getAge(), result.getAge());
        assertEquals(request.getSpendType1st(), result.getSpendType1st());
        assertEquals(request.getSpendType2nd(), result.getSpendType2nd());
        assertEquals(request.getGender(), result.getGender());
        assertEquals(request.getIncomeMin(), result.getIncomeMin());
        assertEquals(request.getIncomeMax(), result.getIncomeMax());
        assertEquals(request.getCreditScore(), result.getCreditScore());
    }

}