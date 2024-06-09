package com.fastcampus.fintechservice.db.finance;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SavingRepository extends JpaRepository<Saving, String> {

	List<Saving> findTop5ByKorCoNm(String korCoNm);

	@Query("SELECT d FROM Saving d JOIN d.savingOptions do WHERE do.saveTrm >= :saveStart AND do.saveTrm <= :saveEnd and do.rsrvType = :rsrvType and d.maxLimit >= :maxLimit order by d.intrRateShow desc limit :size")
	List<Saving> findBySaveTrmUpDownAndMaxLimit(@Param("saveStart") Integer saveStart,
		@Param("saveEnd") Integer saveEnd,
		@Param("maxLimit") Long maxLimit,
		@Param("rsrvType") String rsrvType,
		@Param("size") int size);

}
