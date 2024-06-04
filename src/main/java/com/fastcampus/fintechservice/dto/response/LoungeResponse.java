package com.fastcampus.fintechservice.dto.response;

import com.fastcampus.fintechservice.db.lounge.Lounge;
import com.fastcampus.fintechservice.db.lounge.enums.FinancialType;
import lombok.Builder;
import lombok.Getter;


@Builder
@Getter
public class LoungeResponse {

    private String title;
    private String content;
    private String createdDate;
    private String updatedDate;
    private FinancialType financialType;


    public static LoungeResponse from(Lounge lounge) {
        return LoungeResponse.builder()
                .title(lounge.getTitle())
                .content(lounge.getContent())
                .createdDate(lounge.getCreatedAt())
                .updatedDate(lounge.getModifiedAt())
                .financialType(lounge.getFinancialType())
                .build();
    }
}
