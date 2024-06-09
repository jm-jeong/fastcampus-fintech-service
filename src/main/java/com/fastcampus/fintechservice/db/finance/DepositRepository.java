package com.fastcampus.fintechservice.db.finance;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DepositRepository extends JpaRepository<Deposit, String> {

	List<Deposit> findTop5ByKorCoNm(String korCoNm);

	@Query("SELECT d FROM Deposit d JOIN d.depositOptions do WHERE do.saveTrm >= :saveStart AND do.saveTrm <= :saveEnd and d.maxLimit >= :maxLimit order by d.intrRateShow desc limit :size")
	List<Deposit> findBySaveTrmUpDownAndMaxLimit(@Param("saveStart") Integer saveStart,
		@Param("saveEnd") Integer saveEnd,
		@Param("maxLimit") Long maxLimit,
		@Param("size") int size);

}
