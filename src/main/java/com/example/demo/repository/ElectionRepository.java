package com.example.demo.repository;

import com.example.demo.model.Election;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ElectionRepository extends JpaRepository<Election, Long> {
    @Query("SELECT e FROM Election e WHERE e.published = true AND e.startDate <= :currentDate AND  e.endDate > :currentDate ORDER BY e.startDate")
    List<Election> findLiveElections(@Param("currentDate") LocalDateTime currentDate);

    @Query("SELECT e FROM Election e WHERE e.published = true AND e.startDate > :currentDate ORDER BY e.startDate")
    List<Election> findUpcomingElections(@Param("currentDate") LocalDateTime currentDate);

    @Query("SELECT e FROM Election e WHERE e.published = true AND e.endDate <= :currentDate ORDER BY e.startDate")
    List<Election> findFinishedElections(@Param("currentDate") LocalDateTime currentDate);

    @Query
    List<Election> findByPublishedTrue();

    @Query
    List<Election> findByPublishedFalse();

    @Query("SELECT EXISTS(SELECT e.electionId FROM Election e WHERE e.typeId = ?1 AND e.startDate = ?2 AND e.endDate = ?3 )")
    Boolean existsBy(Integer typeId, LocalDateTime startDate, LocalDateTime endDate);

}
