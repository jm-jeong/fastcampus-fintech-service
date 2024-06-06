package com.fastcampus.fintechservice.db.liked;

import com.fastcampus.fintechservice.db.finance.Deposit;
import com.fastcampus.fintechservice.db.finance.Saving;
import com.fastcampus.fintechservice.db.user.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikedRepository extends JpaRepository<Liked, Long> {


    Liked findByUserAndDeposit(UserAccount user, Deposit deposit);

    Liked findByUserAndSaving(UserAccount user, Saving saving);

    List<Liked> findAllByUser(UserAccount user);


}
