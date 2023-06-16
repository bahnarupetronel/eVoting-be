package com.example.demo.service;

import com.example.demo.repository.PoliticalPartyLocalityRepository;
import org.springframework.stereotype.Service;

@Service
public class PoliticalPartyLocalityService {
    private final PoliticalPartyLocalityRepository politicalPartyLocalityRepository;

    public PoliticalPartyLocalityService(PoliticalPartyLocalityRepository politicalPartyLocalityRepository) {
        this.politicalPartyLocalityRepository = politicalPartyLocalityRepository;
    }
}
