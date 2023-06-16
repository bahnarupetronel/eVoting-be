package com.example.demo.utils;


import com.example.demo.model.PoliticalParty;
import com.example.demo.repository.PoliticalPartyRepository;
import com.github.javafaker.Faker;
import java.util.Locale;

public class FakePoliticalPartiesData {
    private final PoliticalPartyRepository politicalPartyRepository;

    public FakePoliticalPartiesData(PoliticalPartyRepository politicalPartyRepository) {
        this.politicalPartyRepository = politicalPartyRepository;
    }

    public void generateFakePartyData() {
        Faker faker = new Faker(new Locale("ro"));

        for (int i = 0; i < 20; i++) {
            String partyName = faker.company().name();
            PoliticalParty politicalParty = new PoliticalParty();
            politicalParty.setName(partyName);
            politicalPartyRepository.save(politicalParty);
        }
    }
}