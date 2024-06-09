package com.fastcampus.fintechservice.dto.response;

import com.fastcampus.fintechservice.db.finance.Deposit;
import com.fastcampus.fintechservice.db.finance.Saving;
import com.fastcampus.fintechservice.db.finance.enums.FinProductType;

import com.fastcampus.fintechservice.dto.FinanceDetailDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FinanceListResponse {

    private FinProductType finProductType;
    private FinanceDetailDto financeDetailDto;



    public static FinanceListResponse fromDepositProductList(Deposit deposit) {

        String[] joins =deposit.getJoinWay().split(",");
        return FinanceListResponse.builder()
                .finProductType(FinProductType.DEPOSIT)
                .financeDetailDto(FinanceDetailDto.builder()
                        .finPrdtNm(deposit.getFinPrdtNm())
                        .korCoNm(deposit.getKorCoNm())
                        .imageBase64(deposit.getBank().getImageName())
                        .intrRateShow(deposit.getIntrRateShow())
                        .intrRate2Show(deposit.getIntrRate2Show())
                        .joinWayList(joins).build())
                        .build();
    }

    public static FinanceListResponse fromSavingProductList(Saving saving) {

        String[] joins =saving.getJoinWay().split(",");
        return FinanceListResponse.builder()
                .finProductType(FinProductType.SAVING)
                .financeDetailDto(FinanceDetailDto.builder()
                        .finPrdtNm(saving.getFinPrdtNm())
                        .korCoNm(saving.getKorCoNm())
                        .imageBase64(saving.getBank().getImageName())
                        .intrRateShow(saving.getIntrRateShow())
                        .intrRate2Show(saving.getIntrRate2Show())
                        .joinWayList(joins).build())
                        .build();
    }

}
