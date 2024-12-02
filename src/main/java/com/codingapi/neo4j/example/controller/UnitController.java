package com.codingapi.neo4j.example.controller;

import com.codingapi.neo4j.example.entity.Unit;
import com.codingapi.neo4j.example.repository.UnitRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/unit")
@AllArgsConstructor
public class UnitController {

    private final UnitRepository unitRepository;

    @PostMapping("/create")
    public Unit create(@RequestBody Unit unit) {
        return unitRepository.save(unit);
    }


}
