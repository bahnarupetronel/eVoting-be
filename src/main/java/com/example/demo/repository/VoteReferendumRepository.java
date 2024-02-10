package com.example.demo.repository;

import com.example.demo.model.VoteReferendum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteReferendumRepository extends JpaRepository<VoteReferendum, Long> {
}
