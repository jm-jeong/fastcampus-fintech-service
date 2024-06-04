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
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "saving_preference")
public class SavingPreference {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "saving_id")
	private String savingId;

	@Column(name = "intr_rate_nm", nullable = false)
	private String intrRateNm;//추가우대금리명

	@Column(name = "intr_rate3", nullable = false)
	private Double intrRate3;//추가우대금리[소수점2자리]

	@Column(name = "intr_rate_detail", length = 2000)
	private String intrRateDetail;//추가우대금리 설명

	@Column(name = "fin_prdt_cd", length = 50)
	private String finPrdtCd;

}
