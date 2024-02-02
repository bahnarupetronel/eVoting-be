package com.example.demo.repository;

import com.example.demo.model.Election;
import com.example.demo.model.PoliticalParty;
import com.example.demo.model.PoliticalPartyLocality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PoliticalPartyLocalityRepository extends JpaRepository<PoliticalPartyLocality, Integer> {
    @Query
    List<PoliticalPartyLocality> findByLocalityId(Integer localityId);
}
