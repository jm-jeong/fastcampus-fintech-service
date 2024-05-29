package com.fastcampus.fintechservice.db.finance;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "deposit_option")
public class DepositOption {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Column //저축 금리 [소수점 2자리]
	private Double intr_rate2;
	@Column //최고 우대금리[소수점 2자리]
	private Double intr_rate;
	@Column(length = 20) //저축 금리 유형명
	private String intr_rate_type_nm;
	@Column(length = 10) //저축 기간[단위: 개월]
	private String save_trm;
	@Column(length = 4) //저축 금리 유형
	private String intr_rate_type;
	@Column
	private String dcls_month;
	@Column
	private String fin_prdt_cd;
	@Column
	private String fin_co_no;

}
