package com.fastcampus.fintechservice.db.finance;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DepositRepository extends JpaRepository<Deposit, String> {

	List<Deposit> findTop5ByKorCoNm(String korCoNm);
}
