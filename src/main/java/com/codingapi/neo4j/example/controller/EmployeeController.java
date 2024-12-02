package com.codingapi.neo4j.example.controller;

import com.codingapi.neo4j.example.entity.Employee;
import com.codingapi.neo4j.example.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/employee")
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    @PostMapping("/create")
    public Employee create(@RequestBody Employee employee){
        return employeeRepository.save(employee);
    }

}
