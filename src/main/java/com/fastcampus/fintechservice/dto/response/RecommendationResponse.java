package com.fastcampus.fintechservice.dto.response;

import java.util.List;

import com.fastcampus.fintechservice.dto.RecommendationDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecommendationResponse {
	List<RecommendationDto> allList;
	List<RecommendationDto> depostList;
	List<RecommendationDto> savingList;
}
