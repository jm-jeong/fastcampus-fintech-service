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
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "deposit_option")
public class DepositOption {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "intr_rate")
	private Double intrRate;//저축 금리 [소수점 2자리]

	@Column(name = "intr_rate2")
	private Double intrRate2;//최고 우대금리[소수점 2자리]

	@Column(name = "intr_rate_type", length = 4)
	private String intrRateType;//저축 금리 유형명

	@Column(name = "save_trm")
	private Integer saveTrm;//저축 기간[단위: 개월]

	@Column(name = "intr_rate_type_nm", length = 20)
	private String intrRateTypeNm;

	@Column(name = "dcls_month")
	private String dclsMonth;

	@Column(name = "fin_co_no")
	private String finCoNo;

	@Column(name = "fin_prdt_cd")
	private String finPrdtCd;

	@Column(name = "deposit_id")
	String depositId;

}

