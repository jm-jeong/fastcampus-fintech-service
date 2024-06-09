package com.fastcampus.fintechservice.db.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserAccount, Long> {
	Optional<UserAccount> findByName(String name);

	Optional<UserAccount> findByEmail(String email);

	boolean existsByEmail(String email);
}
