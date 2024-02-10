package com.example.demo.repository;

import com.example.demo.model.HasUserVoted;
import org.springframework.data.jpa.repository.JpaRepository;


public interface HasUserVotedRepository extends JpaRepository<HasUserVoted, Long> {

}