package com.example.demo.repository;

import com.example.demo.model.HasUserVoted;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface HasUserVotedRepository extends JpaRepository<HasUserVoted, Long> {

    @Query(value = "SELECT * FROM has_user_voted  WHERE user_id = ?1", nativeQuery = true)
    List<HasUserVoted> findByUserId(@Param("userId") Long userId);
}