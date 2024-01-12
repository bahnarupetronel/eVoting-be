package com.example.demo.service;

import com.example.demo.repository.PoliticalPartyLocalityRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PoliticalPartyLocalityService {
    private final PoliticalPartyLocalityRepository politicalPartyLocalityRepository;
}
