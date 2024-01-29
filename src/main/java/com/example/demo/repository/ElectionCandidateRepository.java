package com.example.demo.repository;

import com.example.demo.model.ElectionCandidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ElectionCandidateRepository extends JpaRepository<ElectionCandidate, Long> {
    Optional<ElectionCandidate> findByCandidateIdAndElectionId(Long candidateId, Long electionId);

//    @Query(value = "SELECT e FROM election_candidates e where e.election_id = ?1 and e.competing_in_locality = ?2", nativeQuery = true)
    List<?> findByElectionIdAndLocalityIdAndPoliticalPartyId(@Param("electionId") Long electionId, @Param("localityId") Long localityId, @Param("politicalPartyId") Long politicalPartyId);

    List<?> findByElectionIdAndLocalityId(@Param("electionId") Long electionId, @Param("localityId") Long localityId);

    @Query(value = "SELECT e FROM election_candidates e where e.election_id = ?1", nativeQuery = true)
    List<ElectionCandidate>  findByElectionId(@Param("electionId") Long electionId);

    Boolean existsByElectionId(@Param("electionId") Long electionId);
}