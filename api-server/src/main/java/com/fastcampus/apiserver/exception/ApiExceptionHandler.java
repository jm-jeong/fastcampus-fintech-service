package com.fastcampus.apiserver.exception;

import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fastcampus.common.api.Api;
import com.fastcampus.common.exception.ApiException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
@Order(value = Integer.MIN_VALUE)   // 최우선처리
public class ApiExceptionHandler {

	@ExceptionHandler(value = ApiException.class)
	public ResponseEntity<Api<Object>> apiException(
		ApiException apiException
	) {
		log.error("", apiException);

		var errorCode = apiException.getErrorCodeIfs();

		return ResponseEntity
			.status(errorCode.getHttpStatusCode())
			.body(
				Api.ERROR(errorCode, apiException.getErrorDescription())
			);

	}
}