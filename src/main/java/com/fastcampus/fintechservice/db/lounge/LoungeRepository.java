package com.fastcampus.fintechservice.db.lounge;

import com.fastcampus.fintechservice.db.finance.enums.FinProductType;
import com.fastcampus.fintechservice.dto.response.LoungeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LoungeRepository extends JpaRepository<Lounge, Long> {

    Optional<Lounge> findById(Long postId);
    Page<Lounge> findAllByOrderByViewCountDesc(Pageable pageable);

    Page<Lounge> findAllByFinProductTypeOrderByViewCountDesc(Pageable pageable, FinProductType finProductType);

}
