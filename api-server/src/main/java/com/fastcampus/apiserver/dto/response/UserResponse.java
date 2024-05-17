package com.fastcampus.apiserver.dto.response;

import com.fastcampus.apiserver.dto.UserDto;

public record UserResponse(
	String name,
	String email
) {

	public static UserResponse of(String name, String email) {
		return new UserResponse(name, email);
	}

	public static UserResponse from(UserDto dto) {
		return new UserResponse(
			dto.name(),
			dto.email()
		);
	}

}
