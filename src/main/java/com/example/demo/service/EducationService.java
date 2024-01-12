package com.example.demo.service;

import com.example.demo.model.Candidate;
import com.example.demo.model.Education;
import com.example.demo.repository.CandidateRepository;
import com.example.demo.repository.EducationRepository;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service@RequiredArgsConstructor
public class EducationService {
    private final EducationRepository educationRepository;
    private final CandidateRepository competitorRepository;

    public void createEducationsForCompetitors() {
        Faker faker = new Faker();
        List<Candidate> candidates = competitorRepository.findAll();
        List <Education> educations = new ArrayList<>();
        for (Candidate candidate : candidates) {
            int numEducations = faker.random().nextInt(1, 4); // Generăm un număr între 1 și 3
            for (int i = 0; i < numEducations; i++) {
                Education education = new Education();
                education.setFaculty(faker.university().name());
                education.setPromotionYear(faker.number().numberBetween(1990, 2023));
                education.setCandidate(candidate);
                educations.add(education);
            }
        }
        educationRepository.saveAll(educations);
    }

//    public List<Education> getEducationsByCompetitorId(Long id) {
//        return educationRepository.findByCompetitorId(Math.toIntExact(Long.valueOf(id))).orElse(null);
//    }
}
