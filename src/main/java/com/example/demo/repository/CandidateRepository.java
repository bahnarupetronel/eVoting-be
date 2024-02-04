package com.example.demo.repository;

import com.example.demo.model.Candidate;
import com.example.demo.payload.CandidateByEventAndLocalityResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    @Query(value = "SELECT * FROM candidates WHERE competing_in_locality = ?1", nativeQuery = true)
    List<Candidate> findByLocalityId(@Param("localityId") BigInteger localityId);

    @Query(value = "SELECT * FROM candidates WHERE LOWER(name) = ?1", nativeQuery = true)
    Optional<Candidate> findByName(@Param("name") String name);

    @Query(value = "SELECT id,name, residence FROM candidates", nativeQuery = true)
    List<?> findAllCandidates();

    @Query(value = "SELECT c.id, c.name, p.name, l.name  FROM candidates c join political_party p on c.political_party_id=p.id join localities l on l.id = c.competing_in_locality WHERE event_type_id = ?1", nativeQuery = true)
    List<ArrayList<?>> findByEventTypeId(@Param("typeId") Integer eventTypeId);

    @Query(value = "SELECT c.id, c.name, c.position, c.competing_in_locality, p.name AS political_party_name, p.id AS political_party_id, elcomp.election_id," +
            "CASE WHEN (elcomp.election_id = ?3 and elcomp.candidate_id IS NOT NULL) THEN true ELSE false " +
            "            END AS registered" +
            "            FROM candidates c " +
            " LEFT JOIN" +
            " election_candidates elcomp ON c.id = elcomp.candidate_id AND elcomp.election_id = ?3 " +
            "            JOIN " +
            "   political_party p ON c.political_party_id = p.id" +
            "     WHERE" +
            "   c.event_type_id = ?1 AND c.competing_in_locality = ?2 and c.candidate_type_id = ?4", nativeQuery = true)
    List<ArrayList<?>> findByEventAndLocality(@Param("typeId") Integer typeId, @Param("localityId") Integer localityId, @Param("eventId") Integer eventId, @Param("candidateTypeId") Integer candidateTypeId);
}
