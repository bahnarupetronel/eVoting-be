package com.example.demo.service;

import com.example.demo.model.Competitor;
import com.example.demo.model.Education;
import com.example.demo.repository.CompetitorRepository;
import com.example.demo.repository.EducationRepository;
import com.github.javafaker.Faker;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EducationService {
    private final EducationRepository educationRepository;
    private final CompetitorRepository competitorRepository;

    public EducationService(EducationRepository educationRepository, CompetitorRepository competitorRepository) {
        this.educationRepository = educationRepository;
        this.competitorRepository = competitorRepository;
    }

    public void createEducationsForCompetitors() {
        Faker faker = new Faker();
        List<Competitor> competitors = competitorRepository.findAll();
        List <Education> educations = new ArrayList<>();
        for (Competitor competitor : competitors) {
            int numEducations = faker.random().nextInt(1, 4); // Generăm un număr între 1 și 3
            for (int i = 0; i < numEducations; i++) {
                Education education = new Education();
                education.setFaculty(faker.university().name());
                education.setPromotionYear(faker.number().numberBetween(1990, 2023));
                education.setCompetitor(competitor);
                educations.add(education);
            }
        }
        educationRepository.saveAll(educations);
    }

//    public List<Education> getEducationsByCompetitorId(Long id) {
//        return educationRepository.findByCompetitorId(Math.toIntExact(Long.valueOf(id))).orElse(null);
//    }
}
