package com.example.demo.Service;

import com.example.demo.Controller.ReadingDTO;
import com.example.demo.domain.Reading;

import java.util.Optional;

public interface ReadingService {
    Optional<Reading> getRandomReading();
    ReadingDTO getReadingDTO();
}
