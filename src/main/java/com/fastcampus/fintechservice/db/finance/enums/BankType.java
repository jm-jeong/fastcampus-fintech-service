package com.fastcampus.fintechservice.db.finance.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BankType {
    KOOKMIN("국민은행"),
    //KOOKMIN_BUSINESS("기업은행"),
    KAKAOBANK("주식회사 카카오뱅크"),
    HANA("하나은행"),
    SHINHAN("신한은행"),
    WOORI("우리은행"),
    BUSAN("부산은행"),
    TOSS("토스뱅크 주식회사"),
    //SAEMAEUL("새마을금고"),
    //SBI("SBI저축은행"),
    //KDB("KDB산업은행"),
    JEONBUK("전북은행"),
    //GWANGJU("광주은행"),
    DAEGU("대구은행"),
    SUHYUP("수협은행"),
    ETC("기타");

    private final String name;
}



    // 생성자, Getter/Setter 등