package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.repository.CompetitorRepository;
import com.example.demo.repository.CountyRepository;
import com.example.demo.repository.PoliticalPartyLocalityRepository;
import com.github.javafaker.Faker;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompetitorService {
    private final CompetitorRepository competitorRepository;
    private final PoliticalPartyLocalityRepository politicalPartyLocalityRepository;
    private final CountyRepository countyRepository;
    public CompetitorService(CompetitorRepository competitorRepository, PoliticalPartyLocalityRepository politicalPartyLocalityRepository, CountyRepository countyRepository) {
        this.competitorRepository = competitorRepository;
        this.politicalPartyLocalityRepository = politicalPartyLocalityRepository;
        this.countyRepository = countyRepository;
    }
    public void createCompetitorsFromPoliticalPartyLocality() {
        List <Competitor> newCompetitors = new ArrayList<>();
        List<PoliticalPartyLocality> politicalPartyLocalities = politicalPartyLocalityRepository.findAll();

        List<County> counties = countyRepository.findAll();
        Faker faker = new Faker();

        for (PoliticalPartyLocality politicalPartyLocality : politicalPartyLocalities) {
            Competitor competitor = new Competitor();
            competitor.setName(faker.name().fullName());
            competitor.setGender(faker.demographic().sex());
            competitor.setPosition("Member");
            competitor.setImageUrl("https://evoting-licenta.s3.eu-central-1.amazonaws.com/competitor.png");
            competitor.setBirthDate(faker.date().birthday().toString());

            String fakeDescription = faker.lorem().paragraphs(3).stream()
                        .collect(Collectors.joining("\n\n"));
            competitor.setDescription(fakeDescription);

            competitor.setResidence(faker.lorem().sentence());
            competitor.setPoliticalPartyId(politicalPartyLocality.getPoliticalParty().getId());
            competitor.setLocalityId((int) politicalPartyLocality.getLocality().getId());
            competitor.setEmail(faker.internet().emailAddress());
            competitor.setPhoneNumber(faker.phoneNumber().phoneNumber());
            String randomCounty = counties.get(faker.random().nextInt(counties.size())).getName();
            competitor.setResidence(randomCounty);
            newCompetitors.add(competitor);

        }
        competitorRepository.saveAll(newCompetitors);
    }

    public Competitor getCompetitorById(Integer id) {
        return competitorRepository.findById(Long.valueOf(id)).orElse(null);
    }

    public List<Competitor> getCompetitorsByLocalityId(Long localityId){
        return competitorRepository.findByLocalityId(BigInteger.valueOf(localityId));
    }
}
