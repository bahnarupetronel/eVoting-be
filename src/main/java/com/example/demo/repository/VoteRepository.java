package com.example.demo.repository;

import com.example.demo.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;


public interface VoteRepository extends JpaRepository<Vote, Long> {
    @Query(value = "SELECT l.id, ec.candidate_id, ec.competing_in_locality, pp.name as political_party_name, l.county, l.name, COALESCE(COUNT(v.vote_id), 0) AS voteCount " +
            "FROM election_candidates ec " +
            "JOIN Localities l ON ec.competing_in_locality = l.id " +
            "JOIN political_party pp ON ec.political_party_id = pp.id " +
            "LEFT JOIN Vote v ON v.election_id = ec.election_id AND v.candidate_id = ec.candidate_id AND v.candidate_type_id = ?2 " +
            "WHERE ec.election_id = ?1 " +
            "AND ec.competing_in_locality = ?3 " +
            "GROUP BY l.id, ec.candidate_id, ec.competing_in_locality, pp.name, l.county, l.name ORDER BY voteCount DESC", nativeQuery = true)
    List<Map<String, Object>> countVotesPerCandidateAndLocality(@Param("electionId") Long electionId, @Param("candidateTypeId") Integer candidateTypeId, @Param("localityId") Long localityId);

    @Query(value = "SELECT l.county, pp.name as political_party_name, COALESCE(COUNT(v.vote_id), 0) AS voteCount " +
            "FROM election_candidates ec " +
            "JOIN Localities l ON ec.competing_in_locality = l.id " +
            "JOIN political_party pp ON ec.political_party_id = pp.id " +
            "LEFT JOIN Vote v ON v.election_id = ec.election_id AND v.candidate_id = ec.candidate_id " +
            "WHERE ec.election_id = ?1 " +
            "AND ec.candidate_type_id = ?2 and ec.county = ?3 " +
            "GROUP BY l.county, pp.name ORDER BY voteCount DESC", nativeQuery = true)
    List<Map<String, Object>> countVotesPerCandidateAndCounty(@Param("electionId") Long electionId, @Param("candidateTypeId") Integer candidateTypeId, @Param("county") String county);

    @Query(value = "SELECT ec.candidate_id, c.name as candidateName, pp.name as political_party_name, COALESCE(COUNT(v.vote_id), 0) AS voteCount " +
            "FROM election_candidates ec " +
            "JOIN political_party pp ON ec.political_party_id = pp.id " +
            "JOIN Candidates c ON ec.candidate_id = c.id " +
            "LEFT JOIN Vote v ON v.election_id = ec.election_id AND v.candidate_id = ec.candidate_id AND v.candidate_type_id = ?2 " +
            "WHERE ec.election_id = ?1 " +
            "GROUP BY ec.candidate_id, c.name, pp.name ORDER BY voteCount DESC", nativeQuery = true)
    List<Map<String, Object>> countVotesPerCandidateCountry(@Param("electionId") Long electionId, @Param("candidateTypeId") Integer candidateTypeId);

}