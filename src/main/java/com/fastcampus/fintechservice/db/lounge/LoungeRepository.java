package com.fastcampus.fintechservice.db.lounge;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LoungeRepository extends JpaRepository<Lounge, Long>, LoungeQueryRepository {

    Optional<Lounge> findById(Long postId);


    @Modifying
    @Query("update Lounge l set l.vote1 = l.vote1 + 1 where l = :lounge")
    void increaseVote1(@Param("lounge") Lounge lounge);
    @Modifying
    @Query("update Lounge l set l.vote2 = l.vote2 + 1 where l = :lounge")
    void increaseVote2(@Param("lounge") Lounge lounge);

}
