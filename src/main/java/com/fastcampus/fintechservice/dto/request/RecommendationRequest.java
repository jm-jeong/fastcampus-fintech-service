package com.fastcampus.fintechservice.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class RecommendationRequest {

	private String ageGroup;//group19under, group2024, group2529, group3034, group3539, group4044, group4549, group50up
	private String incomeGroup;//income100down, income100200, income200300, income300400, income400500, income500up
	private Integer savingGoal;//월저축 목표 금액
	private Integer savingStart;//저축 최소 기간
	private Integer savingEnd;//저축 최대 기간
	private String savingType;//free, regular

}
