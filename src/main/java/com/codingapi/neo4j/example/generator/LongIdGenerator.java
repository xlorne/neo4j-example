package com.codingapi.neo4j.example.generator;

import org.springframework.data.neo4j.core.schema.IdGenerator;

public class LongIdGenerator implements IdGenerator<Long> {

    @Override
    public Long generateId(String primaryLabel, Object entity) {
        return System.currentTimeMillis();
    }
}
