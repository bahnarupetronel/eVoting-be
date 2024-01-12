package com.example.demo.repository;

import com.example.demo.model.ElectionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElectionTypesRepository  extends JpaRepository<ElectionType, Long> {

}
