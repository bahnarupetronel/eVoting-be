package com.example.demo.controller;

import com.example.demo.model.Locality;
import com.example.demo.repository.LocalityRepository;
import com.example.demo.service.LocalityService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/localities")
@CrossOrigin(origins = "http://localhost:3000")
public class LocalityController {
    @Autowired
    private LocalityService localityService;

    @PostMapping("/import")
    public void importLocalities() throws IOException {
        localityService.importLocalitiesFromCsv("classpath:files/localitati.csv");
    }

    @GetMapping("/all")
    public List<Map<String, Object>> getLocalities() {
        return localityService.getLocalities();
    }

    @GetMapping("/map")
    public List<?> getLocalitiesForMap() {
        return localityService.getLocalitiesForMap();
    }

    @GetMapping("/{id}")
    public Locality getById(@PathVariable Long id) {
        return localityService.getLocalityById(id);
    }
}
