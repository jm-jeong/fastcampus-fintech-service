package com.fastcampus.fintechservice.dto.request;

import com.fastcampus.fintechservice.db.finance.enums.FinProductType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoungeRequest {


    private String title;
    private String content;
    private FinProductType finProductType;
    private String financialProduct1;
    private String financialProduct2;



}
