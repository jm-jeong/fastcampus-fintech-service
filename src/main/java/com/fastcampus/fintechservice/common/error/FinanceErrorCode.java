package com.fastcampus.fintechservice.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Finance 의 경우 3000번대 에러코드 사용
 */

@Getter
@AllArgsConstructor
public enum FinanceErrorCode implements ErrorCodeIfs {

	FINANCE_NOT_FOUND(400, 3404, "depost 또는 saving 데이터가 없음");

	private final Integer httpStatusCode;
	private final Integer errorCode;
	private final String description;
}
