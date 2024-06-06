package com.fastcampus.fintechservice.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.fastcampus.fintechservice.db.finance.DepositPreference;
import com.fastcampus.fintechservice.db.finance.SavingPreference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FinancePreferenceDto {

	private String financeId;

	private String intrRateNm;//추가우대금리명

	private Double intrRate3;//추가우대금리[소수점2자리]

	private String intrRateDetail;//추가우대금리 설명

	public static FinancePreferenceDto fromDepositPreference(DepositPreference depositPreference) {
		return new FinancePreferenceDto(depositPreference.getDepositId(), depositPreference.getIntrRateNm(),
			depositPreference.getIntrRate3(), depositPreference.getIntrRateDetail());
	}

	public static List<FinancePreferenceDto> toDtoListFromDepositPreference(
		List<DepositPreference> depositPreferences) {
		return depositPreferences.stream()
			.map(FinancePreferenceDto::fromDepositPreference)
			.collect(Collectors.toList());
	}

	public static FinancePreferenceDto fromSavingPreference(SavingPreference savingPreference) {
		return new FinancePreferenceDto(savingPreference.getSavingId(), savingPreference.getIntrRateNm(),
			savingPreference.getIntrRate3(), savingPreference.getIntrRateDetail());
	}

	public static List<FinancePreferenceDto> toDtoListFromSavingPreference(List<SavingPreference> savingPreference) {
		return savingPreference.stream()
			.map(FinancePreferenceDto::fromSavingPreference)
			.collect(Collectors.toList());
	}

}
