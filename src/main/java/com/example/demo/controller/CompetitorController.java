package com.example.demo.controller;

import com.example.demo.dto.CompetitorDTO;
import com.example.demo.model.Competitor;
import com.example.demo.model.Education;
import com.example.demo.service.CompetitorService;
import com.example.demo.service.EducationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/competitors")
public class CompetitorController {
    private final CompetitorService competitorService;
    private final EducationService educationService;

    public CompetitorController(CompetitorService competitorService, EducationService educationService) {
        this.competitorService = competitorService;
        this.educationService = educationService;
    }

    @PostMapping("/create")
    public void createUsers() {
        competitorService.createCompetitorsFromPoliticalPartyLocality();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Competitor> getCompetitorById(@PathVariable("id") Integer id) {
        Competitor competitor = competitorService.getCompetitorById(id);
        if (competitor != null) {
            return ResponseEntity.ok(competitor);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/locality-id/{localityId}")
    public List<Competitor> getCompetitorsByLocalityId(@PathVariable("localityId") Long localityId) {
        List<Competitor> competitors = competitorService.getCompetitorsByLocalityId(localityId);
        return competitors;
    }
}
