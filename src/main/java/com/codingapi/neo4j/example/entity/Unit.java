package com.codingapi.neo4j.example.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node
@Setter
@Getter
@ToString
public class Unit {

    @Id
    private Long id;
    private String name;
    private String address;


}
