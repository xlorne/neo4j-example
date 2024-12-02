package com.codingapi.neo4j.example.controller;

import com.codingapi.neo4j.example.entity.Depart;
import com.codingapi.neo4j.example.repository.DepartRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/depart")
@AllArgsConstructor
public class DepartController {

    private final DepartRepository departRepository;

    @PostMapping("/create")
    public Depart create(@RequestBody Depart depart) {
        return departRepository.save(depart);
    }


}
