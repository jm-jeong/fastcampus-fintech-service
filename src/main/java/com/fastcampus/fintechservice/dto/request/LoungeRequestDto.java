package com.fastcampus.fintechservice.dto.request;

import com.fastcampus.fintechservice.db.lounge.enums.FinancialProductType;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class LoungeRequestDto {


    private String title;
    private String content;
    private FinancialProductType financialProductType;


}
