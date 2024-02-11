package com.example.demo.repository;

import com.example.demo.model.CandidateType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CandidateTypeRepository extends JpaRepository<CandidateType, Long> {
    List<CandidateType> findByElectionTypeId(Integer electionTypeId);

    CandidateType findById(Integer candidateTypeId);
}