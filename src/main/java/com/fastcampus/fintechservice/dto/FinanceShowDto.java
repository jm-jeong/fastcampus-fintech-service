package com.fastcampus.fintechservice.dto;

import static com.fastcampus.fintechservice.common.ImageConverter.*;

import com.fastcampus.fintechservice.db.finance.Deposit;
import com.fastcampus.fintechservice.db.finance.Saving;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FinanceShowDto {
	private String financeId;//depostId or savingId
	private String financeType;
	private String finPrdtNm;//금융상품명

	private String korCoNm;//은행명
	private String bankImageUrl;//은행이미지 url
	private Double intrRateShow;//저축 금리[소수점2자리]
	private Double intrRate2Show;//최고 우대금리[소수점 2자리]
	private Boolean isLiked;//찜 유무

	public static FinanceShowDto fromDeposit(Deposit deposit) {

		return new FinanceShowDto(
			deposit.getDepositId(),
			"DEPOSIT",
			deposit.getFinPrdtNm(),
			deposit.getKorCoNm(),
			convertImageUrl(deposit.getBank().getImageName()),
			deposit.getIntrRateShow(),
			deposit.getIntrRate2Show(),
			false
		);
	}

	public static FinanceShowDto fromSaving(Saving saving) {

		return new FinanceShowDto(
			saving.getSavingId(),
			"SAVING",
			saving.getFinPrdtNm(),
			saving.getKorCoNm(),
			convertImageUrl(saving.getBank().getImageName()),
			saving.getIntrRateShow(),
			saving.getIntrRate2Show(),
			false
		);
	}

	public FinanceShowDto setLiked(boolean isLiked) {
		this.isLiked = isLiked;
		return this;
	}
}
