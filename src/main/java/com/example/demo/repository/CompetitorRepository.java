package com.example.demo.repository;

import com.example.demo.model.Competitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.List;

public interface CompetitorRepository extends JpaRepository<Competitor, Long> {
    @Query(value = "SELECT * FROM competitors WHERE locality_id = ?1", nativeQuery = true)
    List<Competitor> findByLocalityId(@Param("localityId") BigInteger localityId);
}
