package com.example.demo.service;

import com.amazonaws.services.accessanalyzer.model.ResourceNotFoundException;
import com.example.demo.model.County;
import com.example.demo.repository.CountyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.core.io.ResourceLoader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service@RequiredArgsConstructor
public class CountyService {
    private final CountyRepository countyRepository;
    private final ResourceLoader resourceLoader;

    public void importCountiesFromCsv(String csvFilePath) throws IOException {
        Resource resource = resourceLoader.getResource(csvFilePath);
        InputStream inputStream = resource.getInputStream();

        List<County> counties = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(";");
                County county = new County();
                county.setName(columns[1]);

                counties.add(county);
            }
        }

        countyRepository.saveAll(counties);
    }

    public List<County> getCounties () {
        return countyRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    public County getCountyById (Integer id) {
        return countyRepository.findById(Long.valueOf(id)).orElseThrow(() -> new ResourceNotFoundException(new String("Localitatea cu id-ul " + id + " nu exista!")));
    }

    public List<?> getCountiesForMap(){
        return countyRepository.findCountiesForMap();
    }

    public County getCountyByName (String county) {
        return countyRepository.findByName(county).orElse(null);
    }

}