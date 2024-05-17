package com.fastcampus.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * User의 경우 1000번대 에러코드 사용
 */
@AllArgsConstructor
@Getter
public enum UserErrorCode implements ErrorCodeIfs {
	USER_NOT_FOUND(400, 1404, "사용자 이메일을 찾을 수 없음."),
	DUPLICATED_USER_NAME(400, 1409, "중복된 사용자."),
	INVALID_PASSWORD(401, 1401, "유효하지 않은 패스워드."),
	INVALID_TOKEN(401, 1401, "유효하지 않은 토큰"),
	;

	private final Integer httpStatusCode;
	private final Integer errorCode;
	private final String description;
}
