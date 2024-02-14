package com.example.demo.repository;

import com.example.demo.model.ElectionCandidate;
import com.example.demo.payload.RegisteredCandidates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface ElectionCandidateRepository extends JpaRepository<ElectionCandidate, Long> {
    Optional<ElectionCandidate> findByCandidateIdAndElectionId(Long candidateId, Long electionId);

    @Query(value = "SELECT c.id, c.name, p.name, e.election_candidate_id FROM election_candidates e " +
            "join candidates c " +
            "on e.candidate_id = c.id " +
            "join political_party p" +
            " on p.id = e.political_party_id " +
            "where e.election_id = ?1 and e.competing_in_locality = ?2 and c.candidate_type_id = ?3 ", nativeQuery = true)
    List<ArrayList<?>> findRegisteredCandidates(@Param("electionId") Long electionId, @Param("localityId") Long localityId, @Param("typeId") Long typeId);

    @Query(value = "SELECT e FROM election_candidates e where e.election_id = ?1", nativeQuery = true)
    List<ElectionCandidate>  findByElectionId(@Param("electionId") Long electionId);

    @Query(value = "SELECT e.candidate_id FROM election_candidates e where e.election_id = ?1 and e.competing_in_locality = ?2 and e.candidate_type_id=?3", nativeQuery = true)
    List<Long>  findByElectionIdAndCompetingInLocality(@Param("electionId") Long electionId, @Param("competingInLocality") Long competingInLocality, @Param("candidateTypeId") Long candidateTypeId);

    @Query(value = "SELECT e.candidate_id FROM election_candidates e where e.election_id = ?1 and e.candidate_type_id=?2 and e.county = ?3", nativeQuery = true)
    List<Long>  findByElectionIdAndCompetingInCounty(@Param("electionId") Long electionId, @Param("candidateTypeId") Long candidateTypeId, @Param("county") String county);

    @Query(value = "SELECT e.candidate_id FROM election_candidates e where e.election_id = ?1 and e.candidate_type_id = ?2", nativeQuery = true)
    List<Long>  findByElectionIdAndCandidateTypeId(@Param("electionId") Long electionId, @Param("candidateTypeId") Long candidateTypeId);


    Boolean existsByElectionId(@Param("electionId") Long electionId);

    @Query(value = "SELECT EXISTS(SELECT e FROM election_candidates e where e.election_id = ?1 and e.candidate_id = ?2 and e.competing_in_locality = ?3)", nativeQuery = true)
    Boolean isCandidateRegistered(Long electionId, Long candidateId, Integer localityId);
}