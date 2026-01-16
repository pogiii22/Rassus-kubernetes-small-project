package com.example.demo.Service.impl;

import com.example.demo.Controller.ReadingDTO;
import com.example.demo.Service.ReadingService;
import com.example.demo.dao.ReadingRepository;
import com.example.demo.domain.Reading;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
public class ReadingServiceImpl implements ReadingService {
    private static final Logger logger = Logger.getLogger(ReadingServiceImpl.class.getName());
    @Autowired
    private ReadingRepository repository;

    public Optional<Reading> getRandomReading() {
        long brojAktivnihSekundi = java.time.Instant.now().getEpochSecond() / 60;
        long red = (brojAktivnihSekundi % 100) + 1;

        // repository.findById očekuje Long
        return repository.findById(red);
    }

    @Override
    public ReadingDTO getReadingDTO() {
        Optional<Reading> reading = getRandomReading();
        logger.info("Poslana vlaznost: " + reading.get().getHumidity());
        ReadingDTO dto = new ReadingDTO("Humidity", "%", reading.get().getHumidity());
        return dto;
    }
}
