package com.example.demo.controller;

import com.example.demo.repository.LocalityRepository;
import com.example.demo.service.LocalityService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/localities")
public class LocalityController {
    private final LocalityService localityService;
    private final LocalityRepository localityRepository;

    public LocalityController(LocalityService localityService, LocalityRepository localityRepository){
        this.localityService = localityService;
        this.localityRepository = localityRepository;
    }

    @PostMapping("/import")
    public void importLocalities() throws IOException {
        localityService.importLocalitiesFromCsv("classpath:files/localitati.csv");
    }

    @GetMapping("/all")
    public List<Map<String, Object>> getAllLocalities() {
        return localityRepository.findAllLocalities();
    }
}
