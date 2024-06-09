package com.fastcampus.fintechservice.db.finance;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DepositOptionRepository extends JpaRepository<DepositOption, Long> {

	@Query("select distinct d.depositId from DepositOption d where d.saveTrm >= :saveStart and d.saveTrm <= :saveEnd")
	List<String> findDistinctDepositIdBySaveTrm(@Param("saveStart") Integer saveStart,
		@Param("saveEnd") Integer saveEnd);

}
