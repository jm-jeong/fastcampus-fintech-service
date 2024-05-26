package com.fastcampus.fintechservice.db.lounge;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoungeRepository extends JpaRepository<Lounge, Long> {

    Optional<Lounge> findByPostId(String postId);

}
