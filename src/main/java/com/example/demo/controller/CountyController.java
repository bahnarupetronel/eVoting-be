package com.example.demo.controller;

import com.example.demo.model.County;
import com.example.demo.repository.CountyRepository;
import com.example.demo.service.AuthenticationService;
import com.example.demo.service.CountyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/counties")
@RequiredArgsConstructor
public class CountyController {
    private final CountyService countyService;
    private final CountyRepository countyRepository;
    private final AuthenticationService authenticationService;

    @PostMapping("/import")
    public void importCounties() throws IOException {
        countyService.importCountiesFromCsv("classpath:files/judete.csv");
    }

    @GetMapping("/all")
    public List<County> getAllCounties() {
        return countyRepository.findAll();
    }

    @GetMapping("/test")
    public String testEndpoint() {
         return "Hello from the secured endpoint";    }
}
