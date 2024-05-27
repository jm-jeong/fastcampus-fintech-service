package com.fastcampus.fintechservice.dto.request;

import com.fastcampus.fintechservice.db.lounge.enums.FinancialType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoungeRequestDto {


    private String title;
    private String content;
    private FinancialType financialType;


}
