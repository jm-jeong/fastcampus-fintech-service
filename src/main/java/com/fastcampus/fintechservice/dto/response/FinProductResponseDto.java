package com.fastcampus.fintechservice.dto.response;

import com.fastcampus.fintechservice.dto.LoungeFinanceDto;
import lombok.Builder;
import lombok.Getter;


@Builder
@Getter
public class FinProductResponseDto {
    private LoungeFinanceDto loungeFinanceDto1;
    private LoungeFinanceDto loungeFinanceDto2;

    public static FinProductResponseDto of(LoungeFinanceDto loungeFinanceDto1, LoungeFinanceDto loungeFinanceDto2) {

        return FinProductResponseDto.builder()
                .loungeFinanceDto1(loungeFinanceDto1)
                .loungeFinanceDto2(loungeFinanceDto2)
                .build();
    }

}
