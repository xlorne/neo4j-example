package com.codingapi.neo4j.example;

import com.codingapi.neo4j.example.llm.UnitGenerator;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVTest {

    private final UnitGenerator unitGenerator = new UnitGenerator();

    @Test
    public void createUnit() throws IOException {

        List<String> history = new ArrayList<>();
        String fileName = "units.csv";
        StringBuilder sb = new StringBuilder();
        sb.append("id,unitName,unitAddress\n");
        FileUtils.write(new File(fileName), sb.toString(), "UTF-8");

        for (int i = 0; i < 1000; i++) {
            sb = new StringBuilder();
            int index = i +1;

            String unit = unitGenerator.generate(history);
            history.add(unit);

            sb.append(index).append(",").append(unit).append(",").append(unit+"的地址").append(index).append("\n");
            FileUtils.write(new File(fileName), sb.toString(), "UTF-8",true);
        }
    }
}
