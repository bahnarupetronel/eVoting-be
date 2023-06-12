package com.example.demo.repository;

import com.example.demo.model.Locality;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalityRepository extends JpaRepository<Locality, Long> {
}