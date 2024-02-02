package com.example.demo.service;

import com.example.demo.model.PoliticalPartyLocality;
import com.example.demo.repository.PoliticalPartyLocalityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PoliticalPartyLocalityService {
    private final PoliticalPartyLocalityRepository politicalPartyLocalityRepository;

    public List<PoliticalPartyLocality> getPoliticalPartiesByLocality(String localityId){
        return politicalPartyLocalityRepository.findByLocalityId(Integer.valueOf(localityId));
    }
}
