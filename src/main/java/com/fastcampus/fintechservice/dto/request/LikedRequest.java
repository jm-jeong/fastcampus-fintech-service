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
public class LikedRequest {
	@NotNull
	@NotBlank
	private String id;

	@NotNull
	@NotBlank
	private String type;
}
