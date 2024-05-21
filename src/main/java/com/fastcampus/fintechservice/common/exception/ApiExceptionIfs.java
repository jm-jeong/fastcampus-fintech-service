package com.fastcampus.fintechservice.common.exception;

import com.fastcampus.fintechservice.common.error.ErrorCodeIfs;

public interface ApiExceptionIfs {
	ErrorCodeIfs getErrorCodeIfs();

	String getErrorDescription();
}