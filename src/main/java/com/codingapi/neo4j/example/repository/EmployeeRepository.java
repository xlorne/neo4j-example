package com.codingapi.neo4j.example.repository;

import com.codingapi.neo4j.example.entity.Employee;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface EmployeeRepository extends Neo4jRepository<Employee,Long> {

}
