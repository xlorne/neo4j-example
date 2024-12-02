package com.codingapi.neo4j.example.repository;

import com.codingapi.neo4j.example.entity.Depart;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface DepartRepository extends Neo4jRepository<Depart,Long> {

}
