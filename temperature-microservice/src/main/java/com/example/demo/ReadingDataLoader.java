package com.example.demo;

import com.example.demo.domain.Reading;
import com.example.demo.dao.ReadingRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@Configuration
public class ReadingDataLoader {

    @Bean
    CommandLineRunner loadReadings(ReadingRepository repository) {
        return args -> {

            if (repository.count() > 0) {
                return;
            }

            var path = "/data/readings.csv";

            try (BufferedReader reader = Files.newBufferedReader(Paths.get(path), StandardCharsets.UTF_8))
            /*var inputStream = getClass()
                    .getClassLoader()
                    .getResourceAsStream("readings[7].csv");

            if (inputStream == null) {
                throw new IllegalStateException("readings.csv not found in resources");
            }

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(inputStream, StandardCharsets.UTF_8))) */{


                reader.lines()
                        .skip(1)
                        .map(line -> line.split(",", -1))
                        .map(values -> new Reading(
                                parseFloat(values[0]),
                                parseFloat(values[1]),
                                parseFloat(values[2]),
                                parseFloat(values[3]),
                                parseFloat(values[4]),
                                parseFloat(values[5])
                        ))
                        .forEach(repository::save);
            }
        };
    }

    private Float parseFloat(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return Float.valueOf(value.trim());
    }
}
