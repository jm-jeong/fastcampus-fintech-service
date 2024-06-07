package com.fastcampus.fintechservice.dto;

import static com.fastcampus.fintechservice.common.ImageConverter.*;

import java.io.IOException;

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
	private String imageBase64;//은행이미지 인코딩 값
	private Double intrRateShow;//저축 금리[소수점2자리]
	private Double intrRate2Show;//최고 우대금리[소수점 2자리]
	private Boolean isLiked;//찜 유무

	public static FinanceShowDto fromDeposit(Deposit deposit) {
		// 은행 이미지 base64로 인코딩
		String imageBase64 = "";
		try {
			imageBase64 = encodeBase64(deposit.getBank().getImageName());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new FinanceShowDto(
			deposit.getDepositId(),
			"DEPOSIT",
			deposit.getFinPrdtNm(),
			deposit.getKorCoNm(),
			imageBase64,
			deposit.getIntrRateShow(),
			deposit.getIntrRate2Show(),
			false
		);
	}

	public static FinanceShowDto fromSaving(Saving saving) {
		// 은행 이미지 base64로 인코딩
		String imageBase64 = "";
		try {
			imageBase64 = encodeBase64(saving.getBank().getImageName());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new FinanceShowDto(
			saving.getSavingId(),
			"SAVING",
			saving.getFinPrdtNm(),
			saving.getKorCoNm(),
			imageBase64,
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
