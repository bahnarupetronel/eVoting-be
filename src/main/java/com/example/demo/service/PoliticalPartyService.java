package com.example.demo.service;

import com.example.demo.dto.PoliticalPartyDTO;
import com.example.demo.model.Locality;
import com.example.demo.model.PoliticalParty;
import com.example.demo.repository.LocalityRepository;
import com.example.demo.repository.PoliticalPartyRepository;
import com.example.demo.utils.FakePoliticalPartiesData;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service@RequiredArgsConstructor
public class PoliticalPartyService {
    @Autowired
    private ModelMapper modelMapper;
    private final PoliticalPartyRepository politicalPartyRepository;
    private final LocalityRepository localityRepository;

    public void createFakeData(){
        FakePoliticalPartiesData fakePoliticalPartiesData = new FakePoliticalPartiesData(this.politicalPartyRepository);
        fakePoliticalPartiesData.generateFakePartyData();
    }

    public void associateLocationToParty() throws Exception {
        // Assuming you have the list of political party IDs and locality IDs
        List<Integer> politicalPartyIds = politicalPartyRepository.getAllPoliticalPartyIds(); // Replace with the method to fetch political party IDs from your repository
        List<Integer> localityIds = localityRepository.getAllLocalityIds(); // Replace with the method to fetch locality IDs from your repository

        // Define the number of associations you want to create for each party and locality
        int associationsPerPartyLocality = 2500;

        Random random = new Random();

        for (Integer partyId : politicalPartyIds) {
            if(partyId > 105)
                for (int i = 0; i < associationsPerPartyLocality; i++) {
                    Integer localityId = localityIds.get(random.nextInt(localityIds.size()));
                    PoliticalParty politicalParty = politicalPartyRepository.findById(partyId).orElse(null); // Replace with your repository method to fetch political party by ID
                    Locality locality = localityRepository.findById(localityId).orElse(null); // Replace with your repository method to fetch locality by ID

                    if (politicalParty != null && locality != null) {
                        if (!politicalParty.getLocalities().contains(locality)) { // Check if association already exists
                            politicalParty.getLocalities().add(locality);
                            politicalPartyRepository.save(politicalParty);
                        }
                    }
                }
        }
    }

    public void addPoliticalParty(PoliticalPartyDTO politicalPartyDTO){
        PoliticalParty politicalParty = modelMapper.map(politicalPartyDTO, PoliticalParty.class);
        politicalPartyRepository.save(politicalParty);
    }
}
