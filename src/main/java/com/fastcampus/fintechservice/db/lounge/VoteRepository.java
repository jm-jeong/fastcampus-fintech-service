package com.fastcampus.fintechservice.db.lounge;


import com.fastcampus.fintechservice.db.user.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findByUserAndLounge(UserAccount user, Lounge lounge);

}
