package com.example.demo.service;
import com.example.demo.model.Locality;
import com.example.demo.repository.LocalityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class LocalityService {
    private final LocalityRepository localityRepository;
    private final ResourceLoader resourceLoader;

    @Autowired
    public LocalityService(LocalityRepository localityRepository, ResourceLoader resourceLoader) {
        this.localityRepository = localityRepository;
        this.resourceLoader = resourceLoader;
    }

    public void importLocalitiesFromCsv(String csvFilePath) throws IOException {
        Resource resource = resourceLoader.getResource(csvFilePath);
        InputStream inputStream = resource.getInputStream();

        List<Locality> localities = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");
                Locality locality = new Locality();
                locality.setName(columns[1]);
                locality.setDiacritics(columns[2]);
                locality.setCounty(columns[3]);
                locality.setAuto(columns[4]);
                locality.setZip(columns[5]);

                String populationValue = columns[6].replace("\"", "");
                int population = populationValue.isEmpty() ? -1 : Integer.parseInt(populationValue);
                locality.setPopulation(population);

                locality.setLat(Double.parseDouble(columns[7].replace("\"", "")));
                locality.setLng(Double.parseDouble(columns[8].replace("\"", "")));

                localities.add(locality);
            }
        }

        localityRepository.saveAll(localities);
    }
}