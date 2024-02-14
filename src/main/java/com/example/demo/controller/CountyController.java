package com.example.demo.controller;

import com.example.demo.model.County;
import com.example.demo.repository.CountyRepository;
import com.example.demo.service.CountyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/counties")
@CrossOrigin(origins = "http://localhost:3000")
public class CountyController { 
    @Autowired
    private CountyService countyService;

    @PostMapping("/import")
    @ResponseStatus(HttpStatus.CREATED)
    public void importCounties() throws IOException {
        countyService.importCountiesFromCsv("classpath:files/judete.csv");
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<County> getAllCounties() {
        return countyService.getCounties();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public County getById(@PathVariable Integer id) {
        return countyService.getCountyById(id);
    }


    @GetMapping("/map")
    public List<?> getCountiesForMap() {
        return countyService.getCountiesForMap();
    }
}
