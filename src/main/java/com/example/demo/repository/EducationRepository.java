package com.example.demo.repository;

import com.example.demo.model.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EducationRepository extends JpaRepository<Education, Integer> {

}
