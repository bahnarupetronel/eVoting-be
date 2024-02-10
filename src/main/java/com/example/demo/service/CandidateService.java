package com.example.demo.service;

import com.amazonaws.services.accessanalyzer.model.ResourceNotFoundException;
import com.example.demo.dto.CandidateDTO;
import com.example.demo.dto.EducationDTO;
import com.example.demo.model.*;
import com.example.demo.payload.CandidateByEventAndLocalityResponse;
import com.example.demo.payload.CandidateByEventResponse;
import com.example.demo.payload.CandidateRequest;
import com.example.demo.repository.CandidateRepository;
import com.example.demo.repository.CountyRepository;
import com.example.demo.repository.PoliticalPartyLocalityRepository;
import com.example.demo.repository.PoliticalPartyRepository;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class CandidateService {
    private final ModelMapper modelMapper;
    private final CandidateRepository candidateRepository;
    private final PoliticalPartyLocalityRepository politicalPartyLocalityRepository;
    private final CountyRepository countyRepository;
    private final PoliticalPartyRepository politicalPartyRepository;
    private final EducationService educationService;

    public void createCandidatesFromPoliticalPartyLocality() {
        List<Candidate> newCompetitors = new ArrayList<>();
        List<PoliticalPartyLocality> politicalPartyLocalities = politicalPartyLocalityRepository.findAll();

        List<County> counties = countyRepository.findAll();
        Faker faker = new Faker();

        for (PoliticalPartyLocality politicalPartyLocality : politicalPartyLocalities) {
            Candidate competitor = new Candidate();
            competitor.setName(faker.name().fullName());
            competitor.setGender(faker.demographic().sex());
            competitor.setPosition("Member");
            competitor.setImageUrl("https://evoting-licenta.s3.eu-central-1.amazonaws.com/competitor.png");
            competitor.setBirthDate(faker.date().birthday().toString());

            String fakeDescription = faker.lorem().paragraphs(3).stream()
                    .collect(Collectors.joining("\n\n"));
            competitor.setDescription(fakeDescription);

            competitor.setPoliticalPartyId(politicalPartyLocality.getPoliticalParty().getId());
            competitor.setCompetingInLocality((int) politicalPartyLocality.getLocality().getId());
            competitor.setEmail(faker.internet().emailAddress());
            competitor.setPhoneNumber(faker.phoneNumber().phoneNumber());
            String randomCounty = counties.get(faker.random().nextInt(counties.size())).getName();
            competitor.setAddress(randomCounty);
            newCompetitors.add(competitor);
        }
        candidateRepository.saveAll(newCompetitors);
    }

    public Candidate getCandidateById(Integer id) {
        return candidateRepository.findById(Long.valueOf(id)).orElse(null);
    }

    public Candidate getCandidateByName(String name) {
        name = name.toLowerCase().replace("-", " ");
        String finalName = name;
        Candidate candidate = candidateRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException(new String("Candidatul cu numele " + finalName + " nu exista!")));
        PoliticalParty politicalParty = politicalPartyRepository.findById(Math.toIntExact(candidate.getPoliticalPartyId())).orElse(null);
        return  candidate;
    }

    public List<Candidate> getCandidatesByLocalityId(Long localityId) {
        return candidateRepository.findByLocalityId(BigInteger.valueOf(localityId));
    }

    public List<?> getAllCandidates() {
        return candidateRepository.findAllCandidates();
    }

    public void addCandidate(CandidateRequest candidateRequest) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Candidate candidate = modelMapper.map(candidateRequest, Candidate.class);
        List <Education> educations = candidate.getEducation();
        candidate.setEducation(null);
        Candidate candidateResponse = candidateRepository.save(candidate);
        System.out.println(candidateResponse.getId());
        educationService.postEducationList(educations, candidateResponse);
    }

    public List<CandidateByEventResponse> getCandidatesByEventTypeId(Integer typeId) {
        List<ArrayList<?>> candidatesResponse = candidateRepository.findByEventTypeId(typeId);
        return candidatesResponse.stream().map(candidate -> mapToCandidateByEventResponse(candidate)).toList();
    }

    public List<CandidateByEventAndLocalityResponse> getCandidatesByEventAndLocality(Integer typeId, Integer localityId, Integer eventId, Integer candidateTypeId) {
        List<ArrayList<?>> candidatesResponse = candidateRepository.findByEventAndLocality(typeId, localityId, eventId, candidateTypeId);

        return candidatesResponse.stream().map(candidate -> mapToCandidateByEventAndLocalityResponse(candidate)).toList();
    }

    private CandidateByEventAndLocalityResponse mapToCandidateByEventAndLocalityResponse(ArrayList<?> candidate) {
        return  new CandidateByEventAndLocalityResponse().builder().id((Integer) candidate.get(0))
                .name((String) candidate.get(1))
                .position((String) candidate.get(2))
                .competingInLocality((Long) candidate.get(3))
                .politicalParty((String) candidate.get(4))
                .politicalPartyId((Integer) candidate.get(5))
                .electionId((Long) candidate.get(6))
                .registered((Boolean) candidate.get(7))
                .build();
    }

    private CandidateByEventResponse mapToCandidateByEventResponse(ArrayList<?> candidate) {
        return  new CandidateByEventResponse().builder().id((Integer) candidate.get(0))
                .name((String) candidate.get(1))
                .politicalParty((String) candidate.get(2))
                .locality((String) candidate.get(3))
                .build();
    }
}
