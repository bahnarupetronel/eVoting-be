package com.example.demo.repository;

import com.example.demo.model.County;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountyRepository extends JpaRepository<County, Long> {
}
