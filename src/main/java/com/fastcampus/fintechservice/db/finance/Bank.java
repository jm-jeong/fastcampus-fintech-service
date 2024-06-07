package com.fastcampus.fintechservice.db.finance;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "bank")
public class Bank {
	@Id
	@Column(name = "fin_co_no", nullable = false)
	private String finCoNo;

	@Column(name = "kor_co_nm", nullable = false)
	private String korCoNm;

	@Column(name = "image_name", nullable = false)
	private String imageName;

}