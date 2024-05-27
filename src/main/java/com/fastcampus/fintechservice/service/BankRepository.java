package com.fastcampus.fintechservice.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import com.fastcampus.fintechservice.dto.BankDTO;

@Repository
public interface BankRepository extends JpaRepository<BankDTO, String> {

}
