package com.example.demo.repository;

import com.example.demo.model.County;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CountyRepository extends JpaRepository<County, Long> {
    Optional<County> findByName(String county);

    @Query(value = "SELECT l.id, l.name, l.lat, l.lng, l.county, l.population " +
            "FROM counties c " +
            "JOIN localities l ON c.residence = l.name  AND c.name = l.county " +
            "ORDER BY l.name", nativeQuery = true)
    List<Map<String, Object>> findCountiesForMap();


}
