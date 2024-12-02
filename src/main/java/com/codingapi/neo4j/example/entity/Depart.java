package com.codingapi.neo4j.example.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Node
@Setter
@Getter
@ToString
public class Depart {
    @Id
    private Long id;
    private String name;

    @Relationship(type = "BELONG_TO")
    private Unit unit;
}
