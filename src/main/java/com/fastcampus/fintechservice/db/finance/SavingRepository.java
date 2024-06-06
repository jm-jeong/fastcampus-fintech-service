package com.fastcampus.fintechservice.db.finance;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SavingRepository extends JpaRepository<Saving, String> {

	List<Saving> findTop5ByKorCoNm(String korCoNm);
}
