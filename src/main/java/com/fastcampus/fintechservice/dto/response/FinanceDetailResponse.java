package com.fastcampus.fintechservice.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.fastcampus.fintechservice.dto.FinanceDetailDto;
import com.fastcampus.fintechservice.dto.FinanceShowDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FinanceDetailResponse {
	FinanceDetailDto financeDto;//금융상품 상세
	List<FinanceShowDto> financeShowDtoList = new ArrayList<>();

}
