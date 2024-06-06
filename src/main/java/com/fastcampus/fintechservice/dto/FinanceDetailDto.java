package com.fastcampus.fintechservice.dto;

import static com.fastcampus.fintechservice.common.ImageConverter.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.fastcampus.fintechservice.db.finance.Deposit;
import com.fastcampus.fintechservice.db.finance.DepositOption;
import com.fastcampus.fintechservice.db.finance.Saving;
import com.fastcampus.fintechservice.db.finance.SavingOption;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FinanceDetailDto {

	private String financeId;//depostId or savingId
	private String financeType;//financeType
	private String finPrdtNm;//금융상품명

	private String korCoNm;//은행명
	private String imageBase64;//은행이미지 인코딩 값

	private ArrayList<String> tagList = new ArrayList<String>();
	private Double intrRateShow;//저축 금리[소수점2자리]
	private Double intrRate2Show;//최고 우대금리[소수점 2자리]

	private Integer joinMin;//가입 기간 최소
	private Integer joinMax;//가입 기간 최대
	private String[] joinWayList;//가입 방법

	private Boolean isLiked;//찜유무

	private List<FinancePreferenceDto> financePreferenceDtoList;

	private String mtrtInt;//만기 후 이자율
	private String etcNote;//기타사항
	private String joinMember;//가입대상

	public static FinanceDetailDto fromDeposit(Deposit deposit) {
		List<DepositOption> depositOptions = deposit.getDepositOptions();
		List<Integer> saveList = new ArrayList<>();
		for (DepositOption depositOption : depositOptions) {
			saveList.add(Integer.parseInt(depositOption.getSaveTrm()));
		}
		Integer joinMin = Collections.min(saveList);
		Integer joinMax = Collections.max(saveList);

		String joinWays = deposit.getJoinWay();
		String[] joinWayList = joinWays.split(",");

		String intrRateTypeNmM = deposit.getIntrRateTypeNmM();//복리
		String intrRateTypeNmS = deposit.getIntrRateTypeNmS();//단리
		ArrayList<String> tagList = new ArrayList<String>();
		tagList.add(intrRateTypeNmM);
		tagList.add(intrRateTypeNmS);
		for (String joinWay : joinWayList) {
			tagList.add(joinWay);
		}
		tagList.removeIf(Objects::isNull);//null제거

		// 은행 이미지 base64로 인코딩
		String imageBase64 = "";
		try {
			imageBase64 = encodeBase64(deposit.getBank().getImageName());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new FinanceDetailDto(
			deposit.getDepositId(),
			"deposit",
			deposit.getFinPrdtNm(),
			deposit.getKorCoNm(),
			imageBase64,
			tagList,
			deposit.getIntrRateShow(),
			deposit.getIntrRate2Show(),
			joinMin,
			joinMax,
			joinWayList,
			false,
			FinancePreferenceDto.toDtoListFromDepositPreference(deposit.getDepositPreferences()),
			deposit.getMtrtInt(),
			deposit.getEtcNote(),
			deposit.getJoinMember()
		);
	}

	public static FinanceDetailDto fromSaving(Saving saving) {
		List<SavingOption> savingOptions = saving.getSavingOptions();
		List<Integer> saveList = new ArrayList<>();
		for (SavingOption savingOption : savingOptions) {
			saveList.add(Integer.parseInt(savingOption.getSaveTrm()));
		}
		Integer joinMin = Collections.min(saveList);
		Integer joinMax = Collections.max(saveList);

		String joinWays = saving.getJoinWay();
		String[] joinWayList = joinWays.split(",");

		String intrRateTypeNmM = saving.getIntrRateTypeNmM();//복리
		String intrRateTypeNmS = saving.getIntrRateTypeNmS();//단리
		String rsrvTypeNmF = saving.getRsrvTypeNmF();//자유적립식
		String rsrvTypeNmS = saving.getRsrvTypeNmS();//정액적립식
		ArrayList<String> tagList = new ArrayList<String>();
		tagList.add(intrRateTypeNmM);
		tagList.add(intrRateTypeNmS);
		tagList.add(rsrvTypeNmF);
		tagList.add(rsrvTypeNmS);
		for (String joinWay : joinWayList) {
			tagList.add(joinWay);
		}

		// 은행 이미지 base64로 인코딩
		String imageBase64 = "";
		try {
			imageBase64 = encodeBase64(saving.getBank().getImageName());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new FinanceDetailDto(
			saving.getSavingId(),
			"deposit",
			saving.getFinPrdtNm(),
			saving.getKorCoNm(),
			imageBase64,
			tagList,
			saving.getIntrRateShow(),
			saving.getIntrRate2Show(),
			joinMin,
			joinMax,
			joinWayList,
			false,
			FinancePreferenceDto.toDtoListFromSavingPreference(saving.getSavingPreferences()),
			saving.getMtrtInt(),
			saving.getEtcNote(),
			saving.getJoinMember()
		);
	}

}
