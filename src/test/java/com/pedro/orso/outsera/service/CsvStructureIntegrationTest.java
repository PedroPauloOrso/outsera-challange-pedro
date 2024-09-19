package com.pedro.orso.outsera.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.web.servlet.MockMvc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
public class CsvStructureIntegrationTest {

    private static final String[] EXPECTED_HEADERS = {"year", "title", "studios", "producers", "winner"};
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCsvStructureIsValid() throws Exception {

        ClassPathResource csvFile = new ClassPathResource("movielist.csv");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(csvFile.getInputStream()))) {
            String headerLine = reader.readLine();
            assert headerLine != null : "CSV file is empty or cannot be read";

            String[] actualHeaders = headerLine.split(";");
            assertEquals(Arrays.asList(EXPECTED_HEADERS), Arrays.asList(actualHeaders),
                    "CSV headers do not match the expected structure");
        }
    }
}