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
    private final CandidateRepository candidateRepository;

    public void createEducationsForCompetitors() {
        Faker faker = new Faker();
        List<Candidate> candidates = candidateRepository.findAll();
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

    public void postEducationList(List<Education> educationsReq,  Candidate candidate) {
        List<Education> educations = new ArrayList<>();
        for (Education educationDTO : educationsReq) {
            Education education = createEducation(educationDTO, candidate);
            educations.add(education);
        }
        educationRepository.saveAll(educations);
    }

    private Education createEducation (Education educationReq, Candidate candidate){
        Education education = new Education();
        education.setFaculty(educationReq.getFaculty());
        education.setPromotionYear(educationReq.getPromotionYear());
        education.setCandidate(candidate);
        System.out.println(education.getCandidate().getId());
        return education;
    }

}
