package com.fastcampus.fintechservice.dto.response;

import com.fastcampus.fintechservice.db.lounge.Lounge;
import com.fastcampus.fintechservice.db.lounge.enums.FinancialProductType;
import lombok.Builder;


@Builder
public class LoungeResponseDto {

    private String title;
    private String content;
    private String createdDate;
    private String updatedDate;
    private FinancialProductType financialProductType;


    public static LoungeResponseDto from(Lounge lounge) {
        return LoungeResponseDto.builder()
                .title(lounge.getTitle())
                .content(lounge.getContent())
                .createdDate(lounge.getCreatedAt())
                .updatedDate(lounge.getModifiedAt())
                .financialProductType(lounge.getFinancialProductType())
                .build();
    }
}
