package com.fastcampus.fintechservice.dto;

import static com.fastcampus.fintechservice.common.ImageConverter.*;

import java.util.ArrayList;
import java.util.Objects;

import com.fastcampus.fintechservice.db.finance.Deposit;
import com.fastcampus.fintechservice.db.finance.Saving;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationDto {
	private String financeId;//depostId or savingId
	private String financeType;
	private String finPrdtNm;//금융상품명

	private String korCoNm;//은행명
	private String bankImageUrl;//은행이미지 url
	private Double intrRateShow;//저축 금리[소수점2자리]
	private Double intrRate2Show;//최고 우대금리[소수점 2자리]
	private Boolean isLiked;//찜 유무
	private ArrayList<String> tagList = new ArrayList<String>();

	public static RecommendationDto fromDeposit(Deposit deposit) {

		String joinWays = deposit.getJoinWay();
		String[] joinWayList = joinWays.split(",");
		ArrayList<String> tagList = new ArrayList<String>();
		tagList.add(deposit.getIntrRateTypeNmM());
		tagList.add(deposit.getIntrRateTypeNmS());
		for (String joinWay : joinWayList) {
			tagList.add(joinWay);
		}
		tagList.removeIf(Objects::isNull);//null제거

		return new RecommendationDto(
			deposit.getDepositId(),
			"DEPOSIT",
			deposit.getFinPrdtNm(),
			deposit.getKorCoNm(),
			convertImageUrl(deposit.getBank().getImageName()),
			deposit.getIntrRateShow(),
			deposit.getIntrRate2Show(),
			false,
			tagList
		);
	}

	public static RecommendationDto fromSaving(Saving saving) {

		String joinWays = saving.getJoinWay();
		String[] joinWayList = joinWays.split(",");
		ArrayList<String> tagList = new ArrayList<String>();
		tagList.add(saving.getIntrRateTypeNmM());
		tagList.add(saving.getIntrRateTypeNmS());
		tagList.add(saving.getRsrvTypeNmF());
		tagList.add(saving.getRsrvTypeNmS());
		for (String joinWay : joinWayList) {
			tagList.add(joinWay);
		}
		tagList.removeIf(Objects::isNull);//null제거

		return new RecommendationDto(
			saving.getSavingId(),
			"SAVING",
			saving.getFinPrdtNm(),
			saving.getKorCoNm(),
			convertImageUrl(saving.getBank().getImageName()),
			saving.getIntrRateShow(),
			saving.getIntrRate2Show(),
			false,
			tagList
		);
	}

	public RecommendationDto setLiked(boolean isLiked) {
		this.isLiked = isLiked;
		return this;
	}
}
