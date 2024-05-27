package com.fastcampus.fintechservice.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "BANK")
public class BankDTO {
	   @Id
	   @Column(name = "fin_co_no")
	   private String finCoNo;
	   @Column(name = "cal_tel")
	   private String calTel;
	   @Column(name = "home_url")
	   private String hompUrl;
	   @Column(name = "dcls_chrg_man")
	   private String dclsChrgMan;
	   @Column(name = "kor_co_nm")
	   private String korCoNm;
	   @Column(name = "dcls_month")
	   private String dclsMonth;
}
