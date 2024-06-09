package com.fastcampus.fintechservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserEmailCheckResponse {
	private String email;
	private boolean isTaken;
}
