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
import lombok.ToString;

@Getter
@Builder
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "deposit")
public class Deposit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Column(length = 50)
	private String fin_prdt_cd;

	@Column(length = 50)
	private String fin_co_no;

	@Column//금융회사 제출일 [YYYYMMDDHH24MI]
	private String fin_co_subm_day;
	@Column
	private String dcls_strt_day;
	@Column
	private String dcls_end_day;
	@Column//최고한도
	private Long max_limit;
	@Column//기타 유의사항
	private String etc_note;
	@Column//가입대상
	private String join_member;
	@Column//가입제한 EX) 1:제한없음, 2:서민전용, 3일부제한
	private String join_deny;
	@Column(columnDefinition = "varchar(2000)")//우대조건
	private String spcl_cnd;
	@Column(columnDefinition = "varchar(2000)")//만기 후 이자율
	private String mtrt_int;
	@Column//가입방법
	private String join_way;
	@Column//금융상품명
	private String fin_prdt_nm;
	@Column//금융회사명
	private String kor_co_nm;
	@Column//공시 제출일[YYYYMM]
	private String dcls_month;
	@Column//020000(은행), 030200(여신전문), 030300(저축은행), 050000(보험), 060000(금융투자)
	private String top_fin_grp_no;

}
