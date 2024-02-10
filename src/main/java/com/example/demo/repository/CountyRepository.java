package com.example.demo.repository;

import com.example.demo.model.County;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountyRepository extends JpaRepository<County, Long> {
    Optional<County> findByName(String county);
}
