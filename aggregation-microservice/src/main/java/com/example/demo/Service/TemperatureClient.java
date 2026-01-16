package com.example.demo.Service;

import com.example.demo.Controller.ReadingDTO;
import com.example.demo.TemperatureConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class TemperatureClient {

    @Autowired
    private TemperatureConfig config;
    private final RestClient restClient;

    public TemperatureClient(@Value("${temperature.service.url}") String temperatureServiceUrl) {
        this.restClient = RestClient.create(temperatureServiceUrl);
    }

    public ReadingDTO getTemperature() {
        ReadingDTO reading = restClient.get()
                .uri("/temperature")
                .retrieve()
                .body(ReadingDTO.class);

        if (reading != null && config.isKelvin()) {
            reading.setValueCelsiusToKelvin();
        }

        return reading;
    }
}
