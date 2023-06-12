package com.example.demo.controller;

import com.example.demo.model.County;
import com.example.demo.repository.CountyRepository;
import com.example.demo.service.CountyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/counties")
public class CountyController {
    private final CountyService countyService;
    private final CountyRepository countyRepository;

    public CountyController(CountyService countyService, CountyRepository countyRepository) {
        this.countyService = countyService;
        this.countyRepository = countyRepository;
    }

    @PostMapping("/import")
    public void importCounties() throws IOException {
        countyService.importCountiesFromCsv("classpath:files/judete.csv");
    }

    @GetMapping("/all")
    public List<County> getAllCounties() {
        return countyRepository.findAll();
    }
}
