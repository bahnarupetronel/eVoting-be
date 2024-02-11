package com.example.demo.service;
import com.amazonaws.services.accessanalyzer.model.ResourceNotFoundException;
import com.example.demo.model.Locality;
import com.example.demo.repository.LocalityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service@RequiredArgsConstructor
public class LocalityService {
    private final LocalityRepository localityRepository;
    private final ResourceLoader resourceLoader;

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

    public List<Map<String, Object>> getLocalities(){
       return localityRepository.findAllLocalities();
    }


    public List<Map<String, Object>> getLocalitiesForMap(){
        return localityRepository.findLocalitiesForMap();
    }

    public Locality getLocalityById(Integer id){
        return localityRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(new String("Localitatea cu id-ul " + id + " nu exista!")));
    }

    public Locality getByName(String name){
        return  localityRepository.findByName(name).orElse(null);
    }
}