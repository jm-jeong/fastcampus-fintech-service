package com.fastcampus.fintechservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class FinanceCompareRequest {

	@NotNull
	@NotBlank
	String financeId1;
	@NotNull
	@NotBlank
	String financeId2;
	@NotNull
	@NotBlank
	String financeType;
}
