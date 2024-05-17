package com.fastcampus.common.exception;

import com.fastcampus.common.error.ErrorCodeIfs;

public interface ApiExceptionIfs {
	ErrorCodeIfs getErrorCodeIfs();

	String getErrorDescription();
}