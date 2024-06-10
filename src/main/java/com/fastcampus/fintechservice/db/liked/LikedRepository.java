package com.fastcampus.fintechservice.db.liked;

import java.util.List;

import com.fastcampus.fintechservice.db.finance.enums.FinProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fastcampus.fintechservice.db.finance.Deposit;
import com.fastcampus.fintechservice.db.finance.Saving;
import com.fastcampus.fintechservice.db.user.UserAccount;

public interface LikedRepository extends JpaRepository<Liked, Long> {

	Liked findByUserAndDeposit(UserAccount user, Deposit deposit);

	Liked findByUserAndSaving(UserAccount user, Saving saving);

	List<Liked> findAllByUser(UserAccount user);
	List<Liked> findAllByUserAndFinProductType(UserAccount user, FinProductType finProductType);

	@Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM Liked u WHERE u.user.id = :id AND u.deposit.depositId = :depositId")
	boolean existsByIdAndDepositId(@Param("id") Long id, @Param("depositId") String depositId);

	@Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM Liked u WHERE u.user.id = :id AND u.saving.savingId = :savingId")
	boolean existsByIdAndSavingId(@Param("id") Long id, @Param("savingId") String savingId);

}
