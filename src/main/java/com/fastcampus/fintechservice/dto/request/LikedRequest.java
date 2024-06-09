package com.fastcampus.fintechservice.dto.request;

import com.fastcampus.fintechservice.db.finance.enums.FinProductType;
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
	private String id;
	private FinProductType type;
}
