package com.example.demo.repository;

import com.example.demo.model.Locality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface LocalityRepository extends JpaRepository<Locality, Integer> {
    @Query(value = "SELECT l.id FROM localities l", nativeQuery = true)
    List<Integer> getAllLocalityIds();

    @Query(value = "SELECT id, name, county FROM localities order by name", nativeQuery = true)
    List<Map<String, Object>> findAllLocalities();

    @Query(value = "SELECT id, name, lat, lng, county, population FROM localities order by id", nativeQuery = true)
    List<Map<String, Object>> findLocalitiesForMap();

    @Query(value = "SELECT id, name, county, population, zip, lat, lng, auto, diacritics FROM localities WHERE id = ?1", nativeQuery = true)
    Optional<Locality> findById(@Param("localityId") Long localityId);

    Optional<Locality> findByName(String name);
}