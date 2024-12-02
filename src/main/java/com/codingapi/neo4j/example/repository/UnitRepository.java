package com.codingapi.neo4j.example.repository;

import com.codingapi.neo4j.example.entity.Unit;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface UnitRepository extends Neo4jRepository<Unit, Long> {


}
