package com.fastcampus.fintechservice.db.finance;

import java.util.LinkedHashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
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
@Table(name = "deposit")
public class Deposit {
	@Id
	@Column(name = "deposit_id", nullable = false)
	private String depositId;

	@Column(name = "intr_rate2_show")
	private Double intrRate2Show;//최고 우대금리[소수점 2자리]

	@Column(name = "intr_rate_show")
	private Double intrRateShow;//저축 금리[소수점2자리]

	@Column(name = "max_limit")
	private Long maxLimit;

	@Column(name = "fin_co_no", length = 50)
	private String finCoNo;

	@Column(name = "fin_prdt_cd", length = 50)
	private String finPrdtCd;

	@Column(name = "dcls_end_day")
	private String dclsEndDay;

	@Column(name = "dcls_month")
	private String dclsMonth;

	@Column(name = "dcls_strt_day")
	private String dclsStrtDay;

	@Column(name = "etc_note")
	private String etcNote;

	@Column(name = "fin_co_subm_day")
	private String finCoSubmDay;//금융회사 제출일 [YYYYMMDDHH24MI]

	@Column(name = "fin_prdt_nm")
	private String finPrdtNm;

	@Column(name = "join_deny")
	private String joinDeny;//가입제한 EX) 1:제한없음, 2:서민전용, 3일부제한

	@Column(name = "join_member")
	private String joinMember;

	@Column(name = "join_way")
	private String joinWay;

	@Column(name = "kor_co_nm")
	private String korCoNm;

	@Column(name = "mtrt_int", length = 2000)
	private String mtrtInt;//만기 후 이자율

	@Column(name = "spcl_cnd", length = 2000)
	private String spclCnd;//우대조건

	@Column(name = "top_fin_grp_no")
	private String topFinGrpNo;//020000(은행), 030200(여신전문), 030300(저축은행), 050000(보험), 060000(금융투자)

	@Column(name = "intr_rate_type_nm_s", length = 20)
	private String intrRateTypeNmS;//단리

	@Column(name = "intr_rate_type_nm_m", length = 20)
	private String intrRateTypeNmM;//복리

	@OneToMany
	@JoinColumn(name = "deposit_id", insertable = false, updatable = false)
	@ToString.Exclude
	private Set<DepositOption> depositOptions = new LinkedHashSet<>();

	@OneToMany
	@JoinColumn(name = "deposit_id", insertable = false, updatable = false)
	@ToString.Exclude
	private Set<DepositPreference> depositPreferences = new LinkedHashSet<>();

}
