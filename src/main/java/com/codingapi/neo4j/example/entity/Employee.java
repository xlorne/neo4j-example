package com.codingapi.neo4j.example.entity;

import com.codingapi.neo4j.example.em.Gender;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Date;

@Node
@Setter
@Getter
@ToString
public class Employee {

    @Id
    private Long id;

    private String name;
    private Date birthday;
    private Gender gender;
    private String address;
    private String phone;

    @Relationship(type = "WORK_FOR")
    private Depart depart;

    @Relationship(type = "FRIEND")
    private Employee friend;

}
