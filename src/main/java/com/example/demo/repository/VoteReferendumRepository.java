package com.example.demo.repository;

import com.example.demo.model.VoteReferendum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface VoteReferendumRepository extends JpaRepository<VoteReferendum, Long> {
    @Query(value = "SELECT ro.option_name , COUNT(vr) AS voteCount FROM referendum_options ro LEFT JOIN vote_referendum vr ON vr.option_id = ro.option_id AND vr.election_id = ?1 GROUP BY ro.option_id, ro.option_name ORDER BY voteCount DESC" , nativeQuery = true)
    List<Map<String, Object>> countVotesPerOption(@Param("electionId") Long electionId);

}
