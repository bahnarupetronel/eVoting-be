package com.example.demo.repository;

import com.example.demo.model.Candidate;
import com.example.demo.model.Election;
import com.example.demo.model.ElectionCandidate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ElectionCandidateRepository extends JpaRepository<ElectionCandidate, Long> {
    Optional<ElectionCandidate> findByCandidateAndElection(Candidate candidate, Election election);

}