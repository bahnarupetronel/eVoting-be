package com.example.demo.repository;

import com.example.demo.model.Locality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface LocalityRepository extends JpaRepository<Locality, Integer> {
    @Query(value = "SELECT l.id FROM localities l", nativeQuery = true)
    List<Integer> getAllLocalityIds();

    @Query(value = "SELECT id, name, county FROM localities", nativeQuery = true)
    List<Map<String, Object>> findAllLocalities();
}