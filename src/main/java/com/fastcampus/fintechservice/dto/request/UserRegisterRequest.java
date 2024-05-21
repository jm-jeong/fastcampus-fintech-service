package com.fastcampus.fintechservice.dto.request;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRegisterRequest(
	@NotNull @NotBlank String name,
	@NotNull @NotBlank @Email String email,
	@NotNull @NotBlank @Length(min = 8, max = 30) String password
) {

	public static UserRegisterRequest of(String name, String email, String password) {
		return new UserRegisterRequest(name, email, password);
	}

}
