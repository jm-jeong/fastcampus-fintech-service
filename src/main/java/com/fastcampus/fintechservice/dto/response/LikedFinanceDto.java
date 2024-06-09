package com.fastcampus.fintechservice.dto.response;

import com.fastcampus.fintechservice.db.finance.enums.FinProductType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LikedFinanceDto {

    private String id;//depostId or savingId
    private FinProductType finProductType;//financeProductType
    private String finPrdtNm;//금융상품명

    private String korCoNm;//은행명
    private String imageBase64;//은행이미지 인코딩 값

    private Double intrRateShow;//저축 금리[소수점2자리]
    private Double intrRate2Show;//최고 우대금리[소수점 2자리]

    private List<String> joinWayList;//가입방법




}