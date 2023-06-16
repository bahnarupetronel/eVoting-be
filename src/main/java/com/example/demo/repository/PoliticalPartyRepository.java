package com.example.demo.repository;

import com.example.demo.model.PoliticalParty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PoliticalPartyRepository extends JpaRepository<PoliticalParty, Integer> {
    @Query(value = "SELECT p.id FROM political_party p", nativeQuery = true)
    List<Integer> getAllPoliticalPartyIds();
}
