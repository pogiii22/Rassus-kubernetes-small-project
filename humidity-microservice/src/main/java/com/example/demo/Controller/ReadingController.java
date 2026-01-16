package com.example.demo.Controller;

import com.example.demo.Service.ReadingService;
import com.example.demo.domain.Reading;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/humidity")
public class ReadingController {
    private static final Logger logger = Logger.getLogger(ReadingController.class.getName());

    @Autowired
    private ReadingService readingService;

    @GetMapping("")
    public ReadingDTO getReading(){
        return readingService.getReadingDTO();
    }
}
