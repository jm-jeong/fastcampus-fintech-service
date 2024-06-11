package com.fastcampus.fintechservice.db.lounge;

import com.fastcampus.fintechservice.db.user.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c JOIN FETCH c.user WHERE c.lounge = :lounge")
    List<Comment> findAllByCommentWithLounge(@Param("lounge") Lounge lounge);

}
