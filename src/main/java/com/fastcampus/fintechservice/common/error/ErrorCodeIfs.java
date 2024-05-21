package com.fastcampus.fintechservice.common.error;

public interface ErrorCodeIfs {
	Integer getHttpStatusCode();

	Integer getErrorCode();

	String getDescription();
}
