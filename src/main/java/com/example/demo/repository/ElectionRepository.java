package com.example.demo.repository;


import com.example.demo.model.Election;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ElectionRepository extends JpaRepository<Election, Long> {
    @Query("SELECT e FROM Election e WHERE e.published = true AND e.startDate <= :currentDate AND  e.endDate > :currentDate ORDER BY e.startDate")
    List<Election> findLiveElections(@Param("currentDate") Date currentDate);

    @Query("SELECT e FROM Election e WHERE e.published = true AND e.startDate > :currentDate ORDER BY e.startDate")
    List<Election> findUpcomingElections(@Param("currentDate") Date currentDate);

    @Query("SELECT e FROM Election e WHERE e.published = true AND e.endDate <= :currentDate ORDER BY e.startDate")
    List<Election> findFinishedElections(@Param("currentDate") Date currentDate);

    @Query
    List<Election> findByPublishedTrue();
}
