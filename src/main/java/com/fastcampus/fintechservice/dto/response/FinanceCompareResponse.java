package com.fastcampus.fintechservice.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.fastcampus.fintechservice.dto.FinanceDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FinanceCompareResponse {
	List<FinanceDto> financeDtoList = new ArrayList<>();

	public void dtoListFromFinance(FinanceDto financeDto1, FinanceDto financeDto2) {
		financeDtoList.add(financeDto1);
		financeDtoList.add(financeDto2);
	}
}
