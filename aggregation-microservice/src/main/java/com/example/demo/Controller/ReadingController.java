package com.example.demo.Controller;

import com.example.demo.Service.AggregatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/getreadings")
public class ReadingController {
    private static final Logger logger = Logger.getLogger(ReadingController.class.getName());

    @Autowired
    private AggregatorService service;

    @GetMapping("")
    public List<ReadingDTO> getReadings() {
        return service.getReadings();
    }
}
