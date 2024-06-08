package com.fastcampus.fintechservice.dto;

import java.io.IOException;

import com.fastcampus.fintechservice.common.ImageConverter;
import com.fastcampus.fintechservice.db.finance.Deposit;
import com.fastcampus.fintechservice.db.finance.Saving;
import com.fastcampus.fintechservice.db.finance.enums.FinProductType;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoungeFinanceDto {

	private String id;//depostId or savingId
	private FinProductType finProductType;//financeProductType
	private String finPrdtNm;//금융상품명

	private String korCoNm;//은행명
	private String imageBase64;//은행이미지 인코딩 값

	private Double intrRateShow;//저축 금리[소수점2자리]
	private Double intrRate2Show;//최고 우대금리[소수점 2자리]

	private Integer joinMin;//가입기간 최솟값
	private Integer joinMax;//가입기간 최댓값

	public static LoungeFinanceDto depositFrom(Deposit deposit) throws IOException {
		Integer min = deposit.getDepositOptions().stream().mapToInt(
			depositOption -> depositOption.getSaveTrm()).min().orElseThrow();
		Integer max = deposit.getDepositOptions().stream().mapToInt(
			depositOption -> depositOption.getSaveTrm()).max().orElseThrow();

		return LoungeFinanceDto.builder()
			.id(deposit.getDepositId())
			.finProductType(FinProductType.DEPOSIT)
			.finPrdtNm(deposit.getFinPrdtNm())
			.korCoNm(deposit.getKorCoNm())
			.imageBase64(ImageConverter.encodeBase64(deposit.getBank().getImageName()))
			.intrRateShow(deposit.getIntrRateShow())
			.intrRate2Show(deposit.getIntrRate2Show())
			.joinMin(min)
			.joinMax(max)
			.build();
	}

	public static LoungeFinanceDto savingFrom(Saving saving) throws IOException {
		Integer min = saving.getSavingOptions().stream().mapToInt(
			savingOption -> savingOption.getSaveTrm()).min().orElseThrow();
		Integer max = saving.getSavingOptions().stream().mapToInt(
			savingOption -> savingOption.getSaveTrm()).max().orElseThrow();

		return LoungeFinanceDto.builder()
			.id(saving.getSavingId())
			.finProductType(FinProductType.SAVING)
			.finPrdtNm(saving.getFinPrdtNm())
			.korCoNm(saving.getKorCoNm())
			.imageBase64(ImageConverter.encodeBase64(saving.getBank().getImageName()))
			.intrRateShow(saving.getIntrRateShow())
			.intrRate2Show(saving.getIntrRate2Show())
			.joinMin(min)
			.joinMax(max)
			.build();
	}
}
