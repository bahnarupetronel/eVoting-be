package com.example.demo.controller;

import com.example.demo.model.ReferendumOptions;
import com.example.demo.service.ReferendumOptionsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/referendum")@AllArgsConstructor
public class ReferendumOptionsController {
    private final ReferendumOptionsService referendumOptionsService;

    @GetMapping("/types")
    @ResponseStatus(HttpStatus.OK)
    public List<ReferendumOptions> getOptions(){
        return referendumOptionsService.getOptions();
    }
}
